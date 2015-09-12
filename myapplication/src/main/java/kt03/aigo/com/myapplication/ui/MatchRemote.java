package kt03.aigo.com.myapplication.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigo.usermodule.ui.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.map.b.s;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.adapter.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyList;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.db.IRDataBase;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.ircode.impl.InfraredSender;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;

public class MatchRemote extends Activity implements OnClickListener,
        CallbackOnInfraredSended {
    private final static String TAG = MatchRemote.class.getSimpleName();

    static Context mContext;
    public static MatchRemote instance;
    private Remote mRemote;
    private int maxIndex = 0;
    private int modelIndex = 0;
    private int idModelSearch = 0;

    public final int GET_IR_KEY_OK = 100;
    public final int GET_IR_KEY_FAIL = 101;

    private Button next, test;
    private TextView indexShow;
    private GridView gvRemoteKeys;
    private IRKeyAdapter rmtKeyAdapter;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        instance = this;
        RemoteApplication.getInstance().addActivity(instance);
        initData();
        createView();
    }

    public void createView() {

        setContentView(R.layout.activity_match_remote);
        indexShow = (TextView) findViewById(R.id.tv_show_index);
        next = (Button) findViewById(R.id.btn_match_next);
        next.setOnClickListener(this);
        test = (Button) findViewById(R.id.btn_match_test);
        test.setOnClickListener(this);
        test.setText(R.string.test);

        gvRemoteKeys = (GridView) findViewById(R.id.gv_remote_key);

        setIndexShow();

        initKeysView();

        dialog = new ProgressDialog(this);
        View view = dialog.getLayoutInflater().inflate(R.layout.dlg_loading, null);
        dialog.setCancelable(true);
        dialog.show();

        dialog.setContentView(view);

      /*  for(int i=0;i<maxIndex;i++){
            idModelSearch = Globals.modelSearchs.get(i).getId();
            Thread thread = new Thread(new GetKeysRunnable(idModelSearch));
            thread.start();

        }*/

    }

    public void initData() {
        maxIndex = Globals.modelSearchs.size();

        getDBRemote();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_match_test:

                Module.getInstance().getRemoteKey(idModelSearch, new Module.OnPostListener<IRKeyList>() {
                    @Override
                    public void onSuccess(IRKeyList result) {

                        List<IRKey> irKeys = result.getIrKeys();
                        if (irKeys != null && irKeys.size() > 0) {
                            // Log.d(TAG,"keys="+irKeys.get(0).toString()+"");
                            Message message = new Message();
                            message.what = GET_IR_KEY_OK;
                            message.obj = irKeys;
                            handler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = GET_IR_KEY_FAIL;
                            message.obj = irKeys;
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailure(String err) {

                        ToastUtil.showToast(getApplicationContext(), err + "");
                    }
                });


                break;
            case R.id.btn_match_next:
                reInitViewPager();
                break;

            default:
                break;
        }
    }

    private void reInitViewPager() {
        modelIndex++;
        if (modelIndex >= maxIndex) {
            //Intent i = new Intent(MatchRemote.this, WarningPager.class);
            //startActivity(i);
        } else {
            setIndexShow();
            getDBRemote();
            initKeysView();
        }
    }

    void getDBRemote() {
        mRemote = new Remote();
        mRemote.setBrand(Globals.MBrand);
        mRemote.setKeys(Globals.modelSearchs.get(modelIndex).getKeys());
        idModelSearch = Globals.modelSearchs.get(modelIndex).getId();
        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
    }

    private void setIndexShow() {
        int indextxt = modelIndex + 1;
        String str = getResources().getString(R.string.count_text) + " ( "
                + indextxt + " / " + maxIndex + " )";
        Log.d(TAG, str);
        indexShow.setText(str);
    }

    private void initKeysView() {

        rmtKeyAdapter = new IRKeyAdapter(mContext, mRemote, this);
        gvRemoteKeys.setAdapter(rmtKeyAdapter);

    }


    private Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_IR_KEY_OK:
                    List<IRKey> keys = (List<IRKey>) msg.obj;
                    if (Globals.deviceID == Globals.AIR_CONDITIONER && isAirDiy(keys)) {
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(Globals.deviceID);
                        mRemote.setAir_keys(keys);
                        mRemote.setKeys(IRDataBase.getAirkeys(mContext, 0));

                        Log.d(TAG,"1111111"+mRemote.toString());
                    } else {
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(Globals.deviceID);
                        mRemote.setKeys(keys);

                        Log.d(TAG,"2222222"+mRemote.toString());
                    }

                    if (RemoteUtils.isDiyAirRemote(mRemote)) {
                        mRemote.setAir_keys((List<IRKey>) msg.obj);
                        mRemote.setKeys(IRDataBase.getAirkeys(mContext, 0));
                        Log.d(TAG,"3333333"+mRemote.toString());
                    }

                    Globals.mRemote = mRemote;

                    //Intent i = new Intent(MatchRemote.this, ConfirmRemoteActivity.class);
                   // startActivity(i);
                    break;

                default:
                    break;
            }
        }
    };


    boolean isAirDiy(List<IRKey> keys) {
        for (IRKey key : keys) {
            if (key != null && key.getName().equalsIgnoreCase("poweroff")) {
                Log.d(TAG, "is diy air");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInfrardSended() {

    }

    @Override
    public void onLongPress(int position) {

    }



    class GetKeysRunnable implements Runnable {

        private Integer idModelSearch;

        GetKeysRunnable(Integer idModelSearch) {
            this.idModelSearch = idModelSearch;
            Toast.makeText(getApplicationContext(), idModelSearch + "", Toast.LENGTH_LONG).show();
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            mRemote = new Remote();
            List<IRKey> irKeys = new ArrayList<IRKey>();
            Gson gson = new Gson();

            String remoteKeyId = Globals.GETSERVERREMOTEKEY
                    + idModelSearch.toString();
            ETLogger.debug("idModelSearch=" + idModelSearch + " remoteKeyId=" + remoteKeyId);
//			String  date = HttpRequest.sendGet(remoteKeyId);
//			Logger.debug(date);
            Object[][] comIrKeys = gson.fromJson(
                    HttpRequest.sendGet(remoteKeyId),
                    new TypeToken<Object[][]>() {
                    }.getType());

            for (Object[] object : comIrKeys) {
                IRKey irKey = new IRKey();
                irKey.setName((String) object[0]);
//				ETLogger.debug((String) object[1]);
                List<Infrared> infs = new ArrayList<Infrared>();
                IRCode ir = new IRCode((String) object[1]);
                Infrared inf = new Infrared(ir);

                infs.add(inf);
                Log.d("MatchRemote","setName="+object[0]+"IRCode1="+object[1]+"IRCode2="+object[2]);
                ir = new IRCode((String) object[2]);
                inf = new Infrared(ir);
                infs.add(inf);
                irKey.setProtocol(0);
                irKey.setInfrareds(infs);
                // irKey.setDescription((String) object[5]);
                irKeys.add(irKey);
            }
            if (irKeys != null && irKeys.size() > 0) {
                Log.d(TAG, "idmodelsearch="+idModelSearch);
                if(idModelSearch ==  Globals.modelSearchs.get(maxIndex-1).getId()){
                    dialog.dismiss();
                }
                Message message = new Message();
                message.what = GET_IR_KEY_OK;
                message.obj = irKeys;
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                message.what = GET_IR_KEY_FAIL;
                message.obj = irKeys;
                handler.sendMessage(message);
            }
        }

    }

}

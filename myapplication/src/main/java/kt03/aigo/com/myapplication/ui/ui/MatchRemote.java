package kt03.aigo.com.myapplication.ui.ui;


import android.app.Activity;
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

import com.aigo.usermodule.ui.util.ToastUtil;

import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.db.IRDataBase;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.ircode.impl.InfraredSender;
import kt03.aigo.com.myapplication.business.task.KeyList;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;

public class MatchRemote extends Activity implements OnClickListener,
        CallbackOnInfraredSended {
    private final static String TAG = MatchRemote.class.getSimpleName();

    static Context mContext;
    public static MatchRemote instance;

    private static String mBrand;

    Remote mRemote;

    private int maxIndex = 0;
    int modelIndex = 0;
    Boolean is_net_air;
    int airType = 0;
    int idModelSearch = 0;
    IInfraredSender mSeneder;

    public final int GET_IR_KEY_OK = 100;
    public final int GET_IR_KEY_FAIL = 101;

    private Button next, test;
    private TextView indexShow;
    private GridView gvRemoteKeys;
    private IRKeyAdapter rmtKeyAdapter;

    // KeyAdapter rmtKeyAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        instance = this;
        RemoteApplication.getInstance().addActivity(instance);
        initData();
        createView();
        mSeneder = new InfraredSender();
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
    }

    public void initData() {
        maxIndex = Globals.modelSearchs.size();

        if (Globals.deviceID == ApplianceType.AIR_CONDITIONER
                ) {
            if (Globals.NETCONNECT == false) {
                airType = 1;
            } else {
                airType = 2;
            }
        } else {
            airType = 0;
        }

        mBrand = Globals.MBrand.getBrand();

        getDBRemote();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_match_test:

                //Thread thread = new Thread(new GetKeysRunnable(idModelSearch));
                //thread.start();

                Module.getInstance().getRemoteKey(idModelSearch, new Module.OnPostListener<KeyList>() {
                    @Override
                    public void onSuccess(KeyList result) {
                        //ToastUtil.showToast(getApplicationContext(), result.toString() + "");

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
        Log.v(TAG, str);

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
                    if (Globals.deviceID == ApplianceType.AIR_CONDITIONER && isAirDiy(keys)) {
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(Globals.deviceID);
                        mRemote.setAir_keys(keys);
                        mRemote.setKeys(IRDataBase.getAirkeys(mContext, 0));
                    } else {
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(Globals.deviceID);
                        mRemote.setKeys(keys);
                    }

                    ETLogger.debug("airtype =" + airType);
                    if (RemoteUtils.isDiyAirRemote(mRemote)) {
                        mRemote.setAir_keys((List<IRKey>) msg.obj);
                        mRemote.setKeys(IRDataBase.getAirkeys(mContext, 0));
                    }


                    Globals.mRemote = mRemote;
                    //Log.d(TAG,"remote="+mRemote.toString()+"");

                    Intent i = new Intent(MatchRemote.this, ConfirmRemoteActivity.class);
                    startActivity(i);
                    break;

                default:
                    break;
            }
        }
    };


    boolean isAirDiy(List<IRKey> keys) {
        for (IRKey key : keys) {
            if (key != null && key.getName().equalsIgnoreCase("poweroff")) {
                ETLogger.debug("is diy air");
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

}

package kt03.aigo.com.myapplication.ui.ui;

import java.util.ArrayList;

import java.util.List;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.db.IRDataBase;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.ircode.impl.InfraredSender;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.Constant;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;


public class MatchRemote extends Activity implements OnClickListener,
        CallbackOnInfraredSended {
    private final static String TAG = "MatchRemote";

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
       // instance = this;
        //RemoteApplication.getInstance().addActivity(instance);
        initData();
        createView();
        mSeneder = new InfraredSender();
    }

    public void createView() {

        setContentView(R.layout.activity_match_remote);
     /*   mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
                View.GONE);
        mTitleBarView.setTitleTextStr(mBrand);
        mTitleBarView.setBtnLeft(R.string.brand);
        mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });*/

        indexShow = (TextView) findViewById(R.id.tv_show_index);

        next = (Button) findViewById(R.id.btn_match_next);
        next.setOnClickListener(this);
        test = (Button) findViewById(R.id.btn_match_test);
        test.setOnClickListener(this);
        test.setText("测试");

        gvRemoteKeys = (GridView) findViewById(R.id.gv_remote_key);

        setIndexShow();

        initKeysView();
    }

    public void initData() {
        maxIndex = Globals.modelSearchs.size();

        if (5 == ApplianceType.AIR_CONDITIONER
                ) {
           /* if(Globals.NETCONNECT == false){
                airType = 1;
            }else {*/
                airType = 2;
            //}
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
                Thread thread = new Thread(new GetKeysRunnable(idModelSearch));
                thread.start();

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
            /*Intent i = new Intent(MatchRemote.this, WarningPager.class);
            startActivity(i);*/
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


    class GetKeysRunnable implements Runnable {

        private Integer idModelSearch;

        GetKeysRunnable(Integer idModelSearch) {
            this.idModelSearch = idModelSearch;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub

            mRemote = new Remote();
            List<IRKey> irKeys = new ArrayList<IRKey>();
            Gson gson = new Gson();

            String remoteKeyId = Constant.GETSERVERREMOTEKEY
                    + idModelSearch.toString();
            ETLogger.debug(remoteKeyId);
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

                ir = new IRCode((String) object[2]);
                inf = new Infrared(ir);
                infs.add(inf);
                irKey.setProtocol(0);
                irKey.setInfrareds(infs);
                // irKey.setDescription((String) object[5]);
                irKeys.add(irKey);
            }
            if (irKeys != null && irKeys.size() > 0) {

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

    private Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_IR_KEY_OK:
                    List<IRKey> keys = (List<IRKey>) msg.obj;
                    if(5==ApplianceType.AIR_CONDITIONER&& isAirDiy(keys)){
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(Globals.deviceID);
                        mRemote.setAir_keys(keys);
                       // mRemote.setKeys(IRDataBase.getAirkeys(mContext, 0));
                        mRemote.setKeys(keys);
                    }else {
                        mRemote = new Remote();
                        mRemote.setBrand(Globals.MBrand);

                        mRemote.setId(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setModel(Globals.MBrand.getBrand() + "_" + idModelSearch);
                        mRemote.setType(5);
                        mRemote.setKeys(keys);
                    }

                    ETLogger.debug("airtype ="+airType);
                  /*  if(RemoteUtils.isDiyAirRemote(mRemote)){
                        mRemote.setAir_keys((List<IRKey>) msg.obj);
                        mRemote.setKeys(IRDataBase.getAirkeys(mContext,  0));
                    }*/

                    Globals.mRemote = mRemote;
                    Log.d(TAG,"remote="+mRemote.toString()+"keys="+mRemote.getKeys());

                    Intent i = new Intent(MatchRemote.this, ConfirmRemoteActivity.class);
                    startActivity(i);
                    break;

                default:
                    break;
            }
        }
    };


    boolean isAirDiy(List<IRKey> keys ){
        for (IRKey key : keys )
        {
            if (key != null && key.getName().equalsIgnoreCase("poweroff"))
            {
                ETLogger.debug("is diy air");
                return true;
            }
        }
        return false;
    }
    @Override
    public void onInfrardSended() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLongPress(int position) {
        // TODO Auto-generated method stub

    }

}
package kt03.aigo.com.myapplication.ui.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etek.ircore.RemoteCore;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.air.AirRemoteStateDisplay;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.Constant;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.Tools;
import kt03.aigo.com.myapplication.kt03.SDKModule;
import kt03.aigo.com.myapplication.ui.MainActivity;

public class ConfirmRemoteActivity extends Activity implements
        View.OnClickListener, CallbackOnInfraredSended {

    private static final String TAG = ConfirmRemoteActivity.class.getSimpleName();
    static Context mContext;
    public static ConfirmRemoteActivity instance;


    ArrayList<String> indexs;

    private Button back;
    /*  Button save;
     Button edit;
     EditText nameEdit;*/
    RelativeLayout airScreenRl;
    private GridView gvRemoteKeys;
    TextView airMode, airTemp, airWindAmount;

    Remote mRemote;
    AirRemoteState airRemoteState;
    IRKeyAdapter rmtKeyAdapter;
    AirRemoteStateMananger arsm;
    List<IRKey> airKeys;

    private EditText mCode;
    private Button mSendCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        instance = this;

        RemoteApplication.getInstance().addActivity(instance);

        createView();
    }

    void createView() {

        setContentView(R.layout.activity_confirm_remote);
        mRemote = Globals.mRemote;
        initKeysView();

        Log.d(TAG, mRemote.toString());

        mCode = (EditText) findViewById(R.id.et_code);
        mSendCode = (Button) findViewById(R.id.btn_send_code);
        back = (Button) findViewById(R.id.btn_match_back);

        mSendCode.setOnClickListener(this);
        back.setOnClickListener(this);

        airScreenRl = (RelativeLayout) findViewById(R.id.rlayout_air_screen);
        airMode = (TextView) findViewById(R.id.air_mode);
        airTemp = (TextView) findViewById(R.id.air_temp);
        airWindAmount = (TextView) findViewById(R.id.air_wind_amout);
        arsm = AirRemoteStateMananger.getInstance(mContext);
        if (mRemote.getType() == ApplianceType.AIR_CONDITIONER) {
            airRemoteState = arsm.getAirRemoteState(mRemote.getId());
            showAirStatus(airRemoteState);
        } else {
            airScreenRl.setVisibility(View.GONE);
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_match_back:
                Intent intent = new Intent(ConfirmRemoteActivity.this, MainActivity.class);
                startActivity(intent);
                break;
           /* case R.id.btn_match_save:
                IRDataBase.saveRemote(mContext, mRemote);
                RemoteApplication.getInstance().quitActivity();
                finish();
                break;*/
            case R.id.btn_send_code:
                //initDialog();

               /* Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();*/

                SDKModule.getInstance().sendIRCode(
                        mCode.getText().toString().trim(),
                        new SDKModule.OnPostListener<Integer>() {
                            @Override
                            public void onSuccess(Integer result) {
                                Toast.makeText(getApplicationContext(), "发送成功",
                                        Toast.LENGTH_SHORT).show();
                                //mShow.setText(mInput.getText().toString().trim());
                                mCode.setText("");
                                Log.d(TAG, result + "");
                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(getApplicationContext(), err,
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, err);
                            }
                        });

                break;
            default:
                break;
        }
    }

    private void initKeysView() {

        gvRemoteKeys = (GridView) findViewById(R.id.gv_remote_key);
        rmtKeyAdapter = new IRKeyAdapter(mContext, mRemote, this);
        gvRemoteKeys.setAdapter(rmtKeyAdapter);


    }


    void showAirStatus(AirRemoteState airRemoteState) {
        if (airRemoteState.getPower() == AirPower.POWER_OFF) {
            airMode.setText(R.string.power_off);
            airTemp.setText("");
            airWindAmount.setText("");
        } else {
            ETLogger.debug("REMOTETEST", "temp =" + airRemoteState.getTemp());
            airMode.setText(AirRemoteStateDisplay.getModeStrId(airRemoteState));
            airTemp.setText("" + AirRemoteStateDisplay.getTempStr(airRemoteState));
            airWindAmount.setText(AirRemoteStateDisplay.getWindStrId(airRemoteState));
        }

    }


    @Override
    public void onLongPress(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onInfrardSended() {
        // TODO Auto-generated method stub
        airRemoteState = arsm.getAirRemoteState(mRemote.getId());
//		Logger.debug("REMOTETEST","airRemoteState = "+airRemoteState.getPower()+"_"+airRemoteState.getRemote_id()+"_"+airRemoteState.getTemp()+"_"+airRemoteState.getTemp_display());
        showAirStatus(airRemoteState);

        List<Infrared> list = Constant.infraredList;

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {

                Infrared infrare = list.get(i);

                byte[] codes = RemoteCore.prontoToETcode(infrare.getFreq(),
                        infrare.getSignal());
                mCode.setText(Tools.bytesToHexString(codes));
            }
        }
    }

}
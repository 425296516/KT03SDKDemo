package kt03.aigo.com.myapplication.ui;

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
import java.util.List;
import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.adapter.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.air.AirRemoteStateDisplay;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.util.Constant;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.Tools;
import kt03.aigo.com.myapplication.kt03.SDKModule;

public class ConfirmRemoteActivity extends Activity implements
        View.OnClickListener, CallbackOnInfraredSended {

    private static final String TAG = ConfirmRemoteActivity.class.getSimpleName();
    static Context mContext;
    public static ConfirmRemoteActivity instance;
    private Button back;
    private RelativeLayout airScreenRl;
    private GridView gvRemoteKeys;
    private TextView airMode, airTemp, airWindAmount;
    private EditText mCode;
    private Button mSendCode;
    private Remote mRemote;
    private AirRemoteState airRemoteState;
    private IRKeyAdapter rmtKeyAdapter;
    private AirRemoteStateMananger arsm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_remote);

        mContext = this;
        instance = this;
        RemoteApplication.getInstance().addActivity(instance);
        createView();
    }

    public void createView() {
        mRemote = Globals.mRemote;
        Log.d(TAG, mRemote.toString());
        mCode = (EditText) findViewById(R.id.et_code);
        mSendCode = (Button) findViewById(R.id.btn_send_code);
        back = (Button) findViewById(R.id.btn_match_back);

        initKeysView();

        airScreenRl = (RelativeLayout) findViewById(R.id.rlayout_air_screen);
        airMode = (TextView) findViewById(R.id.air_mode);
        airTemp = (TextView) findViewById(R.id.air_temp);
        airWindAmount = (TextView) findViewById(R.id.air_wind_amout);
        arsm = AirRemoteStateMananger.getInstance(mContext);
        if (mRemote.getType() == Globals.AIR_CONDITIONER) {
            airRemoteState = arsm.getAirRemoteState(mRemote.getId());
            showAirStatus(airRemoteState);
        } else {
            airScreenRl.setVisibility(View.GONE);
        }

        mSendCode.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_match_back:
                Intent intent = new Intent(ConfirmRemoteActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_send_code:

                SDKModule.getInstance().sendIRCode(mCode.getText().toString().trim(), new SDKModule.OnPostListener<Integer>() {

                    @Override
                    public void onSuccess(Integer result) {
                        Toast.makeText(getApplicationContext(), "发送成功",
                                Toast.LENGTH_SHORT).show();
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
            Log.d(TAG, "temp =" + airRemoteState.getTemp());
            airMode.setText(AirRemoteStateDisplay.getModeStrId(airRemoteState));
            airTemp.setText("" + AirRemoteStateDisplay.getTempStr(airRemoteState));
            airWindAmount.setText(AirRemoteStateDisplay.getWindStrId(airRemoteState));
        }

    }


    @Override
    public void onLongPress(int position) {


    }

    @Override
    public void onInfrardSended() {
        airRemoteState = arsm.getAirRemoteState(mRemote.getId());
        Log.d(TAG, "airRemoteState = " + airRemoteState.getPower() + "_" + airRemoteState.getRemote_id() + "_" + airRemoteState.getTemp() + "_" + airRemoteState.getTemp_display());

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
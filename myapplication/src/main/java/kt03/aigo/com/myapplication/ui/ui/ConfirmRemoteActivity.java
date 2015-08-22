package kt03.aigo.com.myapplication.ui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.air.AirRemoteStateDisplay;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyAdapter;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;

public class ConfirmRemoteActivity extends Activity implements
        View.OnClickListener, CallbackOnInfraredSended {

    private static final String TAG = ConfirmRemoteActivity.class.getSimpleName();
    static Context mContext;


    ArrayList<String> indexs;


    Button back;
    Button save;
    Button edit;
    EditText nameEdit;
    RelativeLayout airScreenRl ;
    private GridView gvRemoteKeys;
    TextView airMode,airTemp,airWindAmount;

    Remote mRemote;
    AirRemoteState airRemoteState;
    IRKeyAdapter rmtKeyAdapter;
    AirRemoteStateMananger arsm;
    List<IRKey> airKeys;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        createView();
    }

    void createView() {

        setContentView(R.layout.activity_confirm_remote);
        mRemote = Globals.mRemote;
        Log.d(TAG, "remote=" + mRemote.toString());
        initKeysView();


        back = (Button) findViewById(R.id.btn_match_back);
        back.setOnClickListener(this);
        save = (Button) findViewById(R.id.btn_match_save);
        save.setOnClickListener(this);
        edit = (Button) findViewById(R.id.btn_match_edit);
        edit.setOnClickListener(this);
        airScreenRl = (RelativeLayout) findViewById(R.id.rlayout_air_screen);
        airMode = (TextView) findViewById(R.id.air_mode);
        airTemp = (TextView) findViewById(R.id.air_temp);
        airWindAmount = (TextView) findViewById(R.id.air_wind_amout);
        arsm = AirRemoteStateMananger.getInstance(mContext);
        if(mRemote.getType()== ApplianceType.AIR_CONDITIONER){
            airRemoteState =arsm.getAirRemoteState(mRemote.getId());
            showAirStatus(airRemoteState);
        }else  {
            //airScreenRl.setVisibility(View.GONE);
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
                finish();
                break;
            case R.id.btn_match_save:
             /*   IRDataBase.saveRemote(mContext, mRemote);
                RemoteApplication.getInstance().quitActivity();*/
                finish();
                break;
            case R.id.btn_match_edit:
                initDialog();
                break;
            default:
                break;
        }
    }

    private void initKeysView() {

        gvRemoteKeys = (GridView) findViewById(R.id.gv_remote_key);
        rmtKeyAdapter = new IRKeyAdapter(mContext, mRemote,this);
        gvRemoteKeys.setAdapter(rmtKeyAdapter);
    }

    void initDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_edit, null);
        dialog.setView(layout);
        nameEdit = (EditText)layout.findViewById(R.id.edit_content);
        nameEdit.setText(mRemote.getName());
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEdit.getText().toString();
                mRemote.setName(name);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        dialog.show();

    }

    void showAirStatus(AirRemoteState airRemoteState){
        if(airRemoteState.getPower()== AirPower.POWER_OFF){
            airMode.setText(R.string.power_off);
            airTemp.setText("");
            airWindAmount.setText("");
        }else {
            ETLogger.debug("REMOTETEST", "temp =" + airRemoteState.getTemp());
            airMode.setText(AirRemoteStateDisplay.getModeStrId(airRemoteState));
            airTemp.setText("" +AirRemoteStateDisplay.getTempStr(airRemoteState));
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
        showAirStatus( airRemoteState);
    }

}
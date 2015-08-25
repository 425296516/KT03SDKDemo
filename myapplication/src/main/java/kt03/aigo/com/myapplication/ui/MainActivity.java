package kt03.aigo.com.myapplication.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.etek.ircore.RemoteCore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.BrandList;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.db.IRDataBase;
import kt03.aigo.com.myapplication.business.db.LocalDB;
import kt03.aigo.com.myapplication.business.db.SPManager;
import kt03.aigo.com.myapplication.business.db.UserDB;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.Tools;
import kt03.aigo.com.myapplication.kt03.SDKModule;
import kt03.aigo.com.myapplication.kt03.bean.ResultObject;
import kt03.aigo.com.myapplication.kt03.util.Constant;


public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mIPAddress;
    private EditText mPort;
    private Button mConnect;
    // 定义界面上的两个文本框
    private EditText mInput;
    private TextView mShow;
    // 定义界面上的一个按钮
    private Button mSend;
    private Button mLearnStart;
    private Button mAirIndex;
    private TextView mTextAirIndex;
    private TextView mTextCurrentTime;
    private Button mWifiSSIDPWD;
    private Button mDiscoverKT03;
    private Button mBtnAir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Module.getInstance().init(this);
        SPManager.getInstance().init(this);
        Globals.NETCONNECT = Tools.testConnectivityManager();
        createDatabase();
        //IRDataBase.getRemoteList(this);

        mBtnAir = (Button) findViewById(R.id.btn_air_conditioner);
        mDiscoverKT03 = (Button) findViewById(R.id.btn_discover_kt03);
        mWifiSSIDPWD = (Button) findViewById(R.id.btn_send_wifi_ssid_password);
        mIPAddress = (EditText) findViewById(R.id.et_ip_address);
        mPort = (EditText) findViewById(R.id.et_port);
        mConnect = (Button) findViewById(R.id.btn_current_time);
        mTextCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mInput = (EditText) findViewById(R.id.et_send);
        mSend = (Button) findViewById(R.id.btn_send);
        mShow = (TextView) findViewById(R.id.show);
        mLearnStart = (Button) findViewById(R.id.btn_learn_start);
        mAirIndex = (Button) findViewById(R.id.btn_air_index);
        mTextAirIndex = (TextView) findViewById(R.id.et_air_index);

        mBtnAir.setEnabled(false);

        mBtnAir.setOnClickListener(this);
        mDiscoverKT03.setOnClickListener(this);
        mWifiSSIDPWD.setOnClickListener(this);
        mAirIndex.setOnClickListener(this);
        mConnect.setOnClickListener(this);
        mLearnStart.setOnClickListener(this);
        mSend.setOnClickListener(this);

        Module.getInstance().getBrandList(new Module.OnPostListener<BrandList>() {
            @Override
            public void onSuccess(BrandList brandList) {

                Globals.MBrands = (ArrayList)brandList.getBrandList();
                mBtnAir.setEnabled(true);
            }

            @Override
            public void onFailure(String err) {

            }
        });

    }

    private void createDatabase() {


        UserDB mUserDB = new UserDB(this);
        try {
            mUserDB.createDataBase();
        } catch (IOException e) {

            e.printStackTrace();
        }

        LocalDB mLocalDB = new LocalDB(this);
        try {
            mLocalDB.createDataBase();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {

        List<Infrared> list = (List<Infrared>) intent
                .getSerializableExtra("INFRARED");

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {

                Infrared infrare = list.get(i);

                byte[] codes = RemoteCore.prontoToETcode(infrare.getFreq(),
                        infrare.getSignal());
                mInput.setText(Tools.bytesToHexString(codes));
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_air_conditioner:

                Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();

                Intent i = new Intent(MainActivity.this,
                        BrandListActivity.class);
                startActivity(i);

                break;

            case R.id.btn_discover_kt03:

                startActivity(new Intent(MainActivity.this,
                        DiscoverKT03Activity.class));

                break;

            case R.id.btn_send_wifi_ssid_password:

                startActivity(new Intent(MainActivity.this,
                        ConnectWifiActivity.class));

                break;

            case R.id.btn_current_time:

                Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();

                final long time = System.currentTimeMillis() / 1000;
                // Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                final String dateString = formatter.format(System
                        .currentTimeMillis());

                SDKModule.getInstance().setTime(time,
                        new SDKModule.OnPostListener<ResultObject>() {
                            @Override
                            public void onSuccess(ResultObject result) {

                                if (result.getResult().getRet() == 0) {
                                    Toast.makeText(getApplicationContext(), "同步成功",
                                            Toast.LENGTH_SHORT).show();
                                    mTextCurrentTime.setText("" + dateString);
                                }
                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(getApplicationContext(), "同步失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                break;

            case R.id.btn_air_index:
                Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();
                SDKModule.getInstance().getAirIndex(
                        new SDKModule.OnPostListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(getApplicationContext(), result,
                                        Toast.LENGTH_SHORT).show();
                                mTextAirIndex.setText(result);
                                Log.d(TAG, result);
                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(getApplicationContext(), err,
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, err);
                            }
                        });

                break;

            case R.id.btn_send:
                Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();
                SDKModule.getInstance().sendIRCode(
                        mInput.getText().toString().trim(),
                        new SDKModule.OnPostListener<Integer>() {
                            @Override
                            public void onSuccess(Integer result) {
                                Toast.makeText(getApplicationContext(), "发送成功",
                                        Toast.LENGTH_SHORT).show();
                                mShow.setText(mInput.getText().toString().trim());
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

            case R.id.btn_learn_start:
                Toast.makeText(getApplicationContext(), "开始学习", Toast.LENGTH_SHORT)
                        .show();
                Constant.URL_KT03 = "http://"
                        + mIPAddress.getText().toString().trim() + ":"
                        + mPort.getText().toString().trim();

                SDKModule.getInstance().setLearnModel(
                        new SDKModule.OnPostListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d(TAG, result);
                                mInput.setText(result);
                                Toast.makeText(getApplicationContext(), "获取到学习码",
                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "停止学习",
                                        Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(String err) {
                                Toast.makeText(MainActivity.this, err,
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, err);
                            }
                        });

                break;
        }
        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

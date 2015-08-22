package jiuwei.kt03sdkdemo.ui;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import jiuwei.kt03sdkdemo.R;
import jiuwei.kt03sdkdemo.business.SDKModule;
import jiuwei.kt03sdkdemo.business.obj.GetLearnCodeObject;
import jiuwei.kt03sdkdemo.business.obj.ResultObject;
import jiuwei.kt03sdkdemo.business.util.Constant;


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
    private int time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDiscoverKT03 = (Button)findViewById(R.id.btn_discover_kt03);
        mWifiSSIDPWD = (Button)findViewById(R.id.btn_send_wifi_ssid_password);
        mIPAddress = (EditText) findViewById(R.id.et_ip_address);
        mPort = (EditText) findViewById(R.id.et_port);
        mConnect = (Button) findViewById(R.id.btn_current_time);
        mTextCurrentTime = (TextView)findViewById(R.id.tv_current_time);
        mInput = (EditText) findViewById(R.id.et_send);
        mSend = (Button) findViewById(R.id.btn_send);
        mShow = (TextView) findViewById(R.id.show);
        mLearnStart = (Button) findViewById(R.id.btn_learn_start);
        mAirIndex = (Button) findViewById(R.id.btn_air_index);
        mTextAirIndex = (TextView) findViewById(R.id.et_air_index);

        mDiscoverKT03.setOnClickListener(this);
        mWifiSSIDPWD.setOnClickListener(this);
        mAirIndex.setOnClickListener(this);
        mConnect.setOnClickListener(this);
        mLearnStart.setOnClickListener(this);
        mSend.setOnClickListener(this);


    }

    private Timer timer;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_discover_kt03:

                startActivity(new Intent(MainActivity.this,DiscoverKT03Activity.class));

                break;

            case R.id.btn_send_wifi_ssid_password:

                startActivity(new Intent(MainActivity.this,ConnectWifiActivity.class));

                break;

            case R.id.btn_current_time:

                Constant.URL_KT03 = "http://" + mIPAddress.getText().toString().trim() + ":" + mPort.getText().toString().trim();

                final long time = System.currentTimeMillis()/1000;
                //Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String dateString = formatter.format(System.currentTimeMillis());


                SDKModule.getInstance().setTime(time,new SDKModule.OnPostListener<ResultObject>() {
                    @Override
                    public void onSuccess(ResultObject result) {

                       if(result.getResult().getRet()==0){
                           Toast.makeText(getApplicationContext(),"同步成功",Toast.LENGTH_SHORT).show();
                           mTextCurrentTime.setText(""+dateString);
                       }

                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(getApplicationContext(),"同步失败",Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.btn_air_index:
                Constant.URL_KT03 = "http://" + mIPAddress.getText().toString().trim() + ":" + mPort.getText().toString().trim();
                SDKModule.getInstance().getAirIndex(new SDKModule.OnPostListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        mTextAirIndex.setText(result);
                        Log.d(TAG, result);
                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, err);
                    }
                });

                break;


            case R.id.btn_send:
                Constant.URL_KT03 = "http://" + mIPAddress.getText().toString().trim() + ":" + mPort.getText().toString().trim();
                SDKModule.getInstance().sendIRCode(mInput.getText().toString().trim(), new SDKModule.OnPostListener<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        Toast.makeText(getApplicationContext(),  "发送成功", Toast.LENGTH_SHORT).show();
                        mShow.setText(mInput.getText().toString().trim());
                        Log.d(TAG, result + "");
                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, err);
                    }
                });

                break;

            case R.id.btn_learn_start:
                Toast.makeText(getApplicationContext(), "开始学习", Toast.LENGTH_SHORT).show();
                Constant.URL_KT03 = "http://" + mIPAddress.getText().toString().trim() + ":" + mPort.getText().toString().trim();
               /* SDKModule.getInstance().startLearn(new SDKModule.OnPostListener<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
                        Toast.makeText(getApplicationContext(), "开始学习", Toast.LENGTH_SHORT).show();
                        if (result == 0) {

                            timer = new Timer();
                            NewTimerTask timerTask = new NewTimerTask();
                            //程序运行后立刻执行任务，每隔100ms执行一次
                            timer.schedule(timerTask, 0, 5000);

                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, err);
                    }
                });*/

                SDKModule.getInstance().setLearnModel(new SDKModule.OnPostListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG,result);
                        mInput.setText(result);
                        Toast.makeText(getApplicationContext(), "获取到学习码", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "停止学习", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(String err) {
                        Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, err);
                    }
                });

                break;
        }
        ;
    }



    public class NewTimerTask extends TimerTask {

        @Override
        public void run() {
            if (time < 10) {
                time++;
                //Toast.makeText(getApplicationContext(),time+"",Toast.LENGTH_SHORT).show();
                Log.d(TAG,"time="+time+"");
                SDKModule.getInstance().getLearnCode(new SDKModule.OnPostListener<GetLearnCodeObject>() {
                    @Override
                    public void onSuccess(GetLearnCodeObject result) {

                        if(result.getResult().getRet()==1){

                        }else if(result.getResult().getRet()==0) {

                            timer.cancel();
                            mInput.setText(result.getCode());
                            Toast.makeText(getApplicationContext(), "获取到学习码", Toast.LENGTH_SHORT).show();
                            SDKModule.getInstance().stopLearn(new SDKModule.OnPostListener() {
                                @Override
                                public void onSuccess(Object result) {
                                    Toast.makeText(getApplicationContext(), "停止学习", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String err) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
            }else {
                if (timer != null)
                    timer.cancel();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
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

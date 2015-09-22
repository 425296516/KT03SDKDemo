package com.aigo.kt03airdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.util.Constant;
import com.aigo.kt03airdemo.ui.fragment.HomeApplianceControlFragment;
import com.aigo.kt03airdemo.ui.fragment.HomeIndoorEnvironmentFragment;
import com.aigo.kt03airdemo.ui.view.ResideLayout;

import java.io.File;

/**
 * String ip = KT03AirModule.getInstance().ipIsWifi(getApplicationContext());
 * <p/>
 * if(ip != null){
 * <p/>
 * Constant.URL_KT03 = "http://"+ip;
 * <p/>
 * initData();
 * }
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout mIndoorLL;
    private LinearLayout mAppliaceLL;

    private ImageView mIndoorImage;
    private ImageView mApplianceImage;

    private TextView mIndoorText;
    private TextView mApplianceText;

    private RelativeLayout mMenuRL;
    private RelativeLayout mShareRL;
    private RelativeLayout mAddLL;
    private TextView mApplianceActionbarText;
    private HomeIndoorEnvironmentFragment mHomeIndoorEnvironmentFragment;
    private HomeApplianceControlFragment mHomeApplianceControlFragment;
    private ResideLayout mMenu;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_1_main);
        mContext = this;

        String ip = KT03AirModule.getInstance().ipIsWifi(getApplicationContext());

        if (ip != null) {
            Constant.URL_KT03 = "http://" + ip;
            Log.d(TAG, Constant.URL_KT03);
        }
        KT03AirModule.getInstance().udpBroadcast();
        initView();
        setListener();

        if (mHomeIndoorEnvironmentFragment == null) {
            mHomeIndoorEnvironmentFragment = new HomeIndoorEnvironmentFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl_1_home_fragment, mHomeIndoorEnvironmentFragment).commit();

        if (mHomeApplianceControlFragment == null) {
            mHomeApplianceControlFragment = new HomeApplianceControlFragment();
        }

        showHomeIndoorEnvironmentFragment();
        //showHomeApplianceControlFragment();
    }

    private void initView() {
        mMenu = (ResideLayout) findViewById(R.id.rl_1_home_menu);
        mIndoorLL = (LinearLayout) findViewById(R.id.ll_1_home_indoor_environment_button);
        mAppliaceLL = (LinearLayout) findViewById(R.id.ll_1_home_appliance_control_button);

        mIndoorImage = (ImageView) findViewById(R.id.iv_1_home_indoor_environment);
        mApplianceImage = (ImageView) findViewById(R.id.iv_1_home_appliance_control);

        mIndoorText = (TextView) findViewById(R.id.tv_1_home_indoor_environment);
        mApplianceText = (TextView) findViewById(R.id.tv_1_home_appliance_control);

        mMenuRL = (RelativeLayout) findViewById(R.id.rl_1_indoor_menu);
        mShareRL = (RelativeLayout) findViewById(R.id.rl_1_indoor_share);
        mAddLL = (RelativeLayout) findViewById(R.id.rl_1_home_add_appliance_control);

        mApplianceActionbarText = (TextView) findViewById(R.id.tv_1_home_actionbar_appliance_control);

    }

    private void setListener() {
        mIndoorLL.setOnClickListener(this);
        mAppliaceLL.setOnClickListener(this);
        mMenuRL.setOnClickListener(this);
        mShareRL.setOnClickListener(this);
        mAddLL.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_1_home_indoor_environment_button) { //室内环境

            showHomeIndoorEnvironmentFragment();
            switchFragment(mHomeApplianceControlFragment, mHomeIndoorEnvironmentFragment);

        } else if (v.getId() == R.id.ll_1_home_appliance_control_button) { //家电控制
            //Toast.makeText(mContext,"敬请期待",Toast.LENGTH_SHORT).show();
            showHomeApplianceControlFragment();
            switchFragment(mHomeIndoorEnvironmentFragment, mHomeApplianceControlFragment);
            //startActivity(new Intent(MainActivity.this,HistoryRecordActivity.class));
        } else if (v.getId() == R.id.rl_1_indoor_menu) { //菜单
            mMenu.openPane();
        } else if (v.getId() == R.id.rl_1_indoor_share) {  //分享
            // startActivity(new Intent(MainActivity.this,DeviceConnectActivity.class));

        } else if (v.getId() == R.id.rl_1_home_add_appliance_control) { //添加
            //startActivity(new Intent(MainActivity.this,AddApplianceControlActivity.class));
            shareMsg("导出文件", "家庭环境监测", "家庭环境监测", getSDPath() + "/KT03/air.xls");
            //showFileChooser();
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }

    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("*/*");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

    /**
     * 显示室内环境的 fragment
     */
    private void showHomeIndoorEnvironmentFragment() {

        mIndoorImage.setImageResource(R.drawable.drw_1_home_indoor_environment_select_icon);
        mApplianceImage.setImageResource(R.drawable.drw_1_home_appliance_control_icon);

        mIndoorText.setTextColor(getResources().getColor(R.color.color_50b017));
        mApplianceText.setTextColor(getResources().getColor(R.color.color_aaaaaa));

        mApplianceActionbarText.setVisibility(View.INVISIBLE);
        mShareRL.setVisibility(View.VISIBLE);
        mAddLL.setVisibility(View.INVISIBLE);
    }

    public void switchFragment(Fragment from, Fragment to) {
        if (from == null || to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中

            transaction.hide(from).add(R.id.fl_1_home_fragment, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
    }

    /**
     * 显示家电控制
     */
    private void showHomeApplianceControlFragment() {

        mIndoorImage.setImageResource(R.drawable.drw_1_home_indoor_environment_icon);
        mApplianceImage.setImageResource(R.drawable.drw_1_home_appliance_control_select_icon);

        mIndoorText.setTextColor(getResources().getColor(R.color.color_aaaaaa));
        mApplianceText.setTextColor(getResources().getColor(R.color.color_50b017));

        mApplianceActionbarText.setVisibility(View.VISIBLE);
        mShareRL.setVisibility(View.INVISIBLE);
        mAddLL.setVisibility(View.VISIBLE);
    }

}

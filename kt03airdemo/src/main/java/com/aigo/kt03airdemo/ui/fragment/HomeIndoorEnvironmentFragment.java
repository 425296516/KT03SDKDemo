package com.aigo.kt03airdemo.ui.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.ui.util.Tools;
import com.aigo.usermodule.ui.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class HomeIndoorEnvironmentFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = HomeIndoorEnvironmentFragment.class.getSimpleName();
    private View mView;
    private Context mContext;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private IndoorECleanFragment mIndoorECleanFragment;
    private IndoorEComfortFragment mIndoorEComfortFragment;
    private IndoorEFreshFragment mIndoorEFreshFragment;
    private IndoorEQuietFragment mIndoorEQuietFragment;

    private PagerAdapter mPagerAdapter;
    private TextView mIaqIndex, mPollutionIndex, mUpdateTime;
    private ProgressDialog mDialog;
    private FragmentManager fragmentManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private long mStartRefresh;
    private long mRefreshTime;
    private int mIndex;
    private ImageView mPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_1_home_indoor_environment, null);
        mContext = getActivity();

        initView();

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog();
        initData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStartRefresh = System.currentTimeMillis();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                swipeRefreshLayout.setRefreshing(true);
                if (!Tools.testConnectivityManager(getActivity())) {
                    ToastUtil.showToast(mContext, "刷新失败，请检查当前网络是否连接正常");
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    initData();
                }
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mRefreshTime = System.currentTimeMillis() - mStartRefresh;
            Log.d(TAG, mRefreshTime + "");
            if (mRefreshTime > 8000) {

                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    ToastUtil.showToast(mContext, "刷新失败，请检查kt03是否连接正常");
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (mDialog != null && mDialog.isShowing()) {
                    ToastUtil.showToast(mContext, "刷新失败，请检查kt03是否连接正常");
                    mDialog.dismiss();
                }
            } else {
                Message message = new Message();
                message.obj = 1;

                handler.sendMessageDelayed(message, 2000);
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        //KT03Module.getInstance().setCancelKT03AirInfo(false);
        //initData();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        //KT03Module.getInstance().setCancelKT03AirInfo(true);

    }


    public void initData() {

        KT03AirModule.getInstance().getKT03AirInfo(new KT03AirModule.OnPostListener<AirIndex>() {
            @Override
            public void onSuccess(AirIndex airIndex) {
                //mAirIndex = airIndex;
                float iaq = Float.parseFloat(airIndex.getIaq());
                mIaqIndex.setText(iaq + "");
                if (iaq >= 0 && iaq < 0.50) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_one_excellent_icon);
                } else if (iaq >= 0.50 && iaq < 1.00) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_two_liang_icon);
                } else if (iaq >= 1.00 && iaq < 1.50) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_three_light_pollution_icon);
                } else if (iaq >= 1.50 && iaq < 2.00) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_four_medium_pollution_icon);
                } else if (iaq >= 2.00 && iaq < 3.00) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_five_severe_pollution_icon);
                } else if (iaq >= 3.00) {
                    mPoint.setImageResource(R.drawable.drw_1_home_air_quality_six_serous_pollution_icon);
                }

                mPollutionIndex.setText(airIndex.getIaqQuality());
                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mUpdateTime.setText("更新时间：" + dataFormat.format(airIndex.getTime() * 1000));

                if(mPagerAdapter !=null){
                    mPagerAdapter.clear();
                }
                initMembers(airIndex);

                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                swipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, airIndex.toString());
            }

            @Override
            public void onFailure(String err) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                Toast.makeText(mContext, err, Toast.LENGTH_SHORT).show();
                Log.d(TAG, err);
            }
        });
    }


    public void progressDialog() {
        mStartRefresh = System.currentTimeMillis();
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);

        mDialog = new ProgressDialog(mContext);
        View view = mDialog.getLayoutInflater().inflate(R.layout.dlg_loading, null);
        mDialog.setCancelable(true);
        mDialog.show();
        mDialog.setContentView(view);
    }

    private void initView() {
        mPoint = (ImageView) mView.findViewById(R.id.iv_point);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tl_1_home_indoor_environment);
        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        mIaqIndex = (TextView) mView.findViewById(R.id.tv_iaq_index);
        mPollutionIndex = (TextView) mView.findViewById(R.id.tv_pollution_index);
        mUpdateTime = (TextView) mView.findViewById(R.id.tv_update_time);

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_discuss_refresh);

    }

    private void initMembers(AirIndex airIndex) {
        mIndoorECleanFragment = new IndoorECleanFragment();
        mIndoorECleanFragment.setData(airIndex);
        mIndoorEComfortFragment = new IndoorEComfortFragment();
        mIndoorEComfortFragment.setData(airIndex);
        mIndoorEFreshFragment = new IndoorEFreshFragment();
        mIndoorEFreshFragment.setData(airIndex);
        mIndoorEQuietFragment = new IndoorEQuietFragment();
        mIndoorEQuietFragment.setData(airIndex);

        fragmentManager = getActivity().getSupportFragmentManager();
        mPagerAdapter = new PagerAdapter(fragmentManager, getActivity());
        mPagerAdapter.addFragment(mIndoorECleanFragment, "Tab 1");
        mPagerAdapter.addFragment(mIndoorEFreshFragment, "Tab 2");
        mPagerAdapter.addFragment(mIndoorEQuietFragment, "Tab 3");
        mPagerAdapter.addFragment(mIndoorEComfortFragment, "Tab 4");

        mViewPager.setAdapter(mPagerAdapter);
        //mViewPager.setCurrentItem(2);

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setCustomView(mPagerAdapter.getTabView(i));
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_1_indoor_menu) { //菜单

        } else if (v.getId() == R.id.rl_1_indoor_share) { //分享

        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> myFragments = new ArrayList<>();
        private final List<String> myFragmentTitles = new ArrayList<>();
        private String[] mTitles = new String[]{"洁净度", "清新度", "安静度", "舒适度"};
        private int[] imageResId = {
                R.drawable.drw_1_home_clean_icon,
                R.drawable.drw_1_home_fresh_icon,
                R.drawable.drw_1_home_quiet_icon,
                R.drawable.drw_1_home_comfort_icon};

        private Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        public void clear(){
            myFragments.clear();
            myFragmentTitles.clear();
        }

        public void addFragment(Fragment fragment, String title) {
            myFragments.add(fragment);
            myFragmentTitles.add(title);
        }

        public View getTabView(int position) {

            // Given you have a custom layout in `res/layout/custom_tab_item.xml` with a TextView and ImageView
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.tab_item_view, null);
            ImageView img = (ImageView) v.findViewById(R.id.iv_1_home_tab_view);
            img.setImageResource(imageResId[position]);
            TextView tv = (TextView) v.findViewById(R.id.tv_1_home_tab_view);
            tv.setText(mTitles[position]);
            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return myFragments.get(position);
        }

        @Override
        public int getCount() {
            return myFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return myFragmentTitles.get(position);
        }


    }

}

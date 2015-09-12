package com.aigo.kt03airdemo.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.ui.AirIndex;

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
    private TextView mIaqIndex,mPollutionIndex,mUpdateTime;
    //private AirIndex mAirIndex;
    private ProgressDialog mDialog;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_home_indoor_environment, null);
        mContext = getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();
        initView();
        initData();

        return mView;

    }

    public void initData(){
        progressDialog();
        KT03AirModule.getInstance().getKT03AirInfo(new KT03AirModule.OnPostListener<AirIndex>() {
            @Override
            public void onSuccess(AirIndex airIndex) {

                mIaqIndex.setText(((int)Float.parseFloat(airIndex.getIaq()))+"");
                mPollutionIndex.setText(airIndex.getIaqQuality());
                SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mUpdateTime.setText("更新时间：" + dataFormat.format(airIndex.getTime()*1000));

                initMembers(airIndex);

                if(mDialog != null && mDialog.isShowing()){
                    mDialog.dismiss();
                }

                Log.d(TAG, airIndex.toString());
            }

            @Override
            public void onFailure(String err) {
                Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
                Log.d(TAG, err);
            }
        });

    }

    public void progressDialog(){
        mDialog = new ProgressDialog(getActivity());
        View view = mDialog.getLayoutInflater().inflate(R.layout.dlg_loading, null);
        mDialog.setCancelable(true);
        mDialog.show();

        mDialog.setContentView(view);
    }

    private void initView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tl_1_home_indoor_environment);
        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        mIaqIndex = (TextView)mView.findViewById(R.id.tv_iaq_index);
        mPollutionIndex = (TextView)mView.findViewById(R.id.tv_pollution_index);
        mUpdateTime =(TextView)mView.findViewById(R.id.tv_update_time);
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

        mPagerAdapter = new PagerAdapter(fragmentManager,getActivity());
        mPagerAdapter.addFragment(mIndoorECleanFragment,"Tab 1");
        mPagerAdapter.addFragment(mIndoorEFreshFragment,"Tab 2");
        mPagerAdapter.addFragment(mIndoorEQuietFragment,"Tab 3");
        mPagerAdapter.addFragment(mIndoorEComfortFragment,"Tab 4");

        mViewPager.setAdapter(mPagerAdapter);
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

    class PagerAdapter extends FragmentPagerAdapter {
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

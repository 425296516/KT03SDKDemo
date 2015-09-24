package com.aigo.kt03airdemo.ui.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.ui.MainActivity;
import com.aigo.kt03airdemo.ui.util.Tools;
import com.aigo.usermodule.ui.LoginActivity;
import com.aigo.usermodule.ui.util.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class HomeApplianceControlFragment extends Fragment {
    private static final String TAG = HomeApplianceControlFragment.class.getSimpleName();
    private List<AirIndex> mAirIndexList;
    private PullToRefreshListView mPullToRefreshListView;
    private ListView mListView;
    private View mView;
    private TextView mTem;
    private TextView mHumidity;
    private TextView mNoise;
    private TextView mCo2;
    private TextView mVoc;
    private TextView mPm25;
    private TextView mFormadehyde;
    private TextView mUpdateTime;
    private LayoutInflater mInflater;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context mContext;

    private long mStartRefresh;
    private long mRefreshTime;
    private MyAdapter mAdapter;
    private View mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        registerBoradcastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_history_record, container, false);
        mInflater = inflater;
        mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
        Collections.reverse(mAirIndexList);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_discuss_refresh);
        mListView = (ListView) mView.findViewById(R.id.list_view);

        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);

        return mView;
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("refresh_data")) {
                mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
                Collections.reverse(mAirIndexList);
                mAdapter.notifyDataSetChanged();
            }
        }

    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh_data");
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                    //initData();
                    mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
                    Collections.reverse(mAirIndexList);
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
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
             /*   if (mDialog != null && mDialog.isShowing()) {
                    ToastUtil.showToast(mContext, "刷新失败，请检查kt03是否连接正常");
                    mDialog.dismiss();
                }*/

            } else {
                Message message = new Message();
                message.obj = 1;

                handler.sendMessageDelayed(message, 2000);
            }
        }
    };

    private AlertDialog mExitConfirmDialog;

    public void loginRemind(final int positon) {

        if (mExitConfirmDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("是否删除？");
            builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    KT03AirModule.getInstance().deleteById(mAirIndexList.get(positon).getId());

                    mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
                    Collections.reverse(mAirIndexList);
                    mAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            mExitConfirmDialog = builder.create();
        }

        if (mExitConfirmDialog.isShowing()) {
            mExitConfirmDialog.dismiss();
        } else {
            mExitConfirmDialog.show();
            //startActivity(new Intent(MainActivity.this, MySettingsActivity.class));
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAirIndexList.size();
        }

        @Override
        public Object getItem(int i) {
            return mAirIndexList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mInflater.inflate(R.layout.item_list_view, null);
            }
            mTem = (TextView) view.findViewById(R.id.tv_temperature_num);
            mHumidity = (TextView) view.findViewById(R.id.tv_humidity_num);
            mNoise = (TextView) view.findViewById(R.id.tv_noise_num);
            mCo2 = (TextView) view.findViewById(R.id.tv_co2_num);
            mVoc = (TextView) view.findViewById(R.id.tv_voc_num);
            mPm25 = (TextView) view.findViewById(R.id.tv_pm25_num);
            mFormadehyde = (TextView) view.findViewById(R.id.tv_formadehyde_num);
            mUpdateTime = (TextView) view.findViewById(R.id.tv_update_time);

            mTem.setText("温度：" + mAirIndexList.get(i).getTemperature() + "℃");
            mHumidity.setText("湿度：" + mAirIndexList.get(i).getHumidity() + "%");
            mNoise.setText("噪音：" + mAirIndexList.get(i).getNoise() + "等级");
            mCo2.setText("Co2：" + mAirIndexList.get(i).getCo2() + "ppm");
            mVoc.setText("Voc：" + mAirIndexList.get(i).getVoc() + "等级");
            mPm25.setText("PM25：" + mAirIndexList.get(i).getPm25() + "mg/m³");
            mFormadehyde.setText("甲醛：" + mAirIndexList.get(i).getFormadehyde() + "ppm");
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
            mUpdateTime.setText("更新时间：" + dataFormat.format(mAirIndexList.get(i).getTime() * 1000));

            /*view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    loginRemind(i);

                    return false;
                }
            });*/

            return view;
        }
    }
}




// mPullToRefreshListView.getPullToRefreshScrollDirection();
//初始化控件
// mPullToRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
//ListView mListView = mPullToRefreshListView.getRefreshableView();
//  mAdapter = new MyAdapter();
// mListView.setAdapter(mAdapter);
//设置pull-to-refresh模式为Mode.Both
//mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
//设置上拉下拉事件
       /* mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override

            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

             *//*   if (refreshView.isShown()) {

                    Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
                    //下拉刷新 业务代码
                } else {*//*

                    mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
                    Collections.reverse(mAirIndexList);
                    mAdapter.notifyDataSetChanged();
                    //mPullToRefreshListView.isRefreshing();
                    mPullToRefreshListView.setRefreshing(false);

                    Toast.makeText(getActivity(), "上拉加载更多", Toast.LENGTH_SHORT).show();
                    //上拉加载更多 业务代码
               // }
            }
        });*/
      /*  //mLoading = mView.findViewById(R.id.loading);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_discuss_refresh);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);*/
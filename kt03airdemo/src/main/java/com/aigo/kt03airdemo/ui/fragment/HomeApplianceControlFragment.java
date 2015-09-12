package com.aigo.kt03airdemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.ui.AirIndex;

import java.text.SimpleDateFormat;
import java.util.List;

public class HomeApplianceControlFragment extends Fragment {
    private List<AirIndex> mAirIndexList;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_history_record, container, false);
        mInflater = inflater;
        mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();
        mListView = (ListView) mView.findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

        return mView;
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
        public View getView(int i, View view, ViewGroup viewGroup) {
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
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
            mUpdateTime.setText("更新时间：" + dataFormat.format(mAirIndexList.get(i).getTime() * 1000));

            return view;
        }
    }
}

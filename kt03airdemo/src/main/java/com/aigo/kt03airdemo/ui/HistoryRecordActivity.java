package com.aigo.kt03airdemo.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class HistoryRecordActivity extends ActionBarActivity {

    private List<AirIndex> mAirIndexList;
    private ListView mListView;

    private TextView mTem;
    private TextView mHumidity;
    private TextView mNoise;
    private TextView mCo2;
    private TextView mVoc;
    private TextView mPm25;
    private TextView mFormadehyde;
    private TextView mUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);


        mAirIndexList = KT03AirModule.getInstance().getHistoryAirIndex();

        mListView = (ListView)findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);

    }


    class MyAdapter extends BaseAdapter{

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
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            View view = getLayoutInflater().inflate(R.layout.item_list_view,null);
            mTem = (TextView) view.findViewById(R.id.tv_temperature_num);
            mHumidity = (TextView) view.findViewById(R.id.tv_humidity_num);
            mNoise = (TextView) view.findViewById(R.id.tv_noise_num);
            mCo2 = (TextView) view.findViewById(R.id.tv_co2_num);
            mVoc = (TextView) view.findViewById(R.id.tv_voc_num);
            mPm25 = (TextView) view.findViewById(R.id.tv_pm25_num);
            mFormadehyde = (TextView) view.findViewById(R.id.tv_formadehyde_num);
            mUpdateTime = (TextView) view.findViewById(R.id.tv_update_time);

            mTem.setText("温度：" + mAirIndexList.get(i).getTemperature() + "");
            mHumidity.setText("湿度：" + mAirIndexList.get(i).getHumidity()+"");
            mNoise.setText("噪音：" + mAirIndexList.get(i).getNoise()+"");
            mCo2.setText("Co2：" + mAirIndexList.get(i).getCo2()+"");
            mVoc.setText("Voc：" + mAirIndexList.get(i).getVoc()+"");
            mPm25.setText("PM25：" + mAirIndexList.get(i).getPm25()+"");
            mFormadehyde.setText("甲醛：" + mAirIndexList.get(i).getFormadehyde()+"");
            SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm");
            mUpdateTime.setText("时间：" + dataFormat.format(mAirIndexList.get(i).getTime()*1000));

            return view;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_history_record, menu);
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

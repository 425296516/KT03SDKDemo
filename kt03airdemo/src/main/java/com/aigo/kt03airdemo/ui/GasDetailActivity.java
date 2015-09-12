package com.aigo.kt03airdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.ui.obj.GasDetailObj;
import com.aigo.kt03airdemo.ui.obj.IndexLevelObj;
import com.aigo.kt03airdemo.ui.util.Constant;
import com.aigo.kt03airdemo.ui.util.UiUtil;
import com.aigo.kt03airdemo.ui.view.GasDetailListView;
import com.goyourfly.ln.Ln;

import java.util.List;

public class GasDetailActivity extends Activity {
    private RelativeLayout mReturnRL;
    private TextView mTitleText;
    private RelativeLayout mShareRL;

    private TextView mCurrentValue;
    private TextView mCurrentLevel;

    private GasDetailListView mLevelList;
    private TextView mSource;
    private TextView mHealthTips;

    private GasDetailObj mGasDetailObj;

    private String mTitleStr;
    private String mValueF;
    private String mLevelStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_detail);

        mTitleStr = getIntent().getStringExtra(Constant.GAS_DETAIL_TYPE);
        mValueF = getIntent().getStringExtra(Constant.GAS_DETAIL_VALUE);
        mLevelStr = getIntent().getStringExtra(Constant.GAS_DETAIL_LEVEL);

        initView();
        initMembers();
    }

    private void initView() {
        mReturnRL = (RelativeLayout) findViewById(R.id.rl_1_gas_detail_return);
        mTitleText = (TextView) findViewById(R.id.tv_1_gas_detail_title);
        mShareRL = (RelativeLayout) findViewById(R.id.rl_1_gas_detail_share);

        mCurrentValue = (TextView) findViewById(R.id.tv_1_gas_detail_current_value);
        mCurrentLevel = (TextView) findViewById(R.id.tv_1_gas_detail_current_level);

        mLevelList = (GasDetailListView) findViewById(R.id.lv_1_gas_detail_list);
        mSource = (TextView) findViewById(R.id.tv_1_gas_detail_source);
        mHealthTips = (TextView) findViewById(R.id.tv_1_gas_detail_health_tips);
    }

    private void initMembers() {
        mTitleText.setText(mTitleStr);
        mCurrentValue.setText(mValueF);
        mCurrentLevel.setText(mLevelStr);

        mGasDetailObj = UiUtil.getGasDetail(mTitleStr, mLevelStr);

        Ln.d("GasDetailActivity:mGasDetailObj.getList():" + mGasDetailObj.getList().size());

        MyAdapter adapter = new MyAdapter();
        adapter.setData(mGasDetailObj.getList());
        adapter.notifyDataSetChanged();
        mLevelList.setAdapter(adapter);

        mSource.setText(mGasDetailObj.getSource());
        mHealthTips.setText(mGasDetailObj.getHealthTips());

        mReturnRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mShareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    class MyAdapter extends BaseAdapter {
        private List<IndexLevelObj> mList;

        public void setData(List<IndexLevelObj> list) {
            this.mList = list;
        }

        @Override
        public int getCount() {
            Ln.d("GasDetailActivity:getCount:i:" + (mList == null ? 0 : mList.size()));
            return mList.size();
        }

        @Override
        public IndexLevelObj getItem(int position) {

            Ln.d("GasDetailActivity:getItem:position:" + position);

            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_1_gas_detail_index_level, null);
            }

            Ln.d("GasDetailActivity:getView:position:" + position);

            IndexLevelObj indexLevelObj = getItem(position);

            ImageView background = (ImageView) view.findViewById(R.id.iv_1_gas_detail_tag_background);
            TextView tag = (TextView) view.findViewById(R.id.tv_1_gas_detail_tag);
            TextView description = (TextView) view.findViewById(R.id.tv_1_gas_detail_description);
            TextView level = (TextView) view.findViewById(R.id.tv_1_gas_detail_index_level_value);

            tag.setText(indexLevelObj.getTag());
            description.setText(indexLevelObj.getDescription());
            level.setText(indexLevelObj.getValue());

            if (indexLevelObj.isMeetStandard()) {
                background.setBackgroundResource(R.drawable.drw_1_gas_detail_tag_background_touch);
                tag.setTextColor(getResources().getColor(R.color.color_50b017));
                description.setTextColor(getResources().getColor(R.color.color_50b017));
            } else {
                background.setBackgroundResource(R.drawable.drw_1_gas_detail_tag_background);
                tag.setTextColor(getResources().getColor(R.color.color_7c7c7c));
                description.setTextColor(getResources().getColor(R.color.color_2a2a2a));
            }

            return view;
        }
    }

}

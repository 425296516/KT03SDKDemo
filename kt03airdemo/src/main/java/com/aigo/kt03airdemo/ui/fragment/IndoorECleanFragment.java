package com.aigo.kt03airdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03Adapter;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.business.ui.AirQuality;
import com.aigo.kt03airdemo.ui.GasDetailActivity;
import com.aigo.kt03airdemo.ui.util.Constant;


public class IndoorECleanFragment extends Fragment {
    private View mView;

    private TextView mPm25Level;
    private TextView mPm25Value;
    private LinearLayout mPm25Background;

    private TextView mFormaldehydeLevel;
    private TextView mFormaldehydeValue;
    private LinearLayout mFormaldehydeBackground;
    private AirIndex mAirIndex;

    public void setData(AirIndex airIndex){
        mAirIndex = airIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_ie_clean,null);
        initView();
        initData();
        initMembers();
        return mView;
    }

    public void initData(){

        mPm25Value.setText(mAirIndex.getPm25()+"");
        mFormaldehydeValue.setText(mAirIndex.getFormadehyde()+"");
        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mPm25Level.setText(airQuality.getPm25());
        mFormaldehydeLevel.setText(airQuality.getFormadehyde());
    }

    private void initView(){
        mPm25Level = (TextView) mView.findViewById(R.id.tv_1_ie_clean_pm25_level);
        mPm25Value = (TextView) mView.findViewById(R.id.tv_1_ie_clean_pm25_value);
        mPm25Background = (LinearLayout)mView.findViewById(R.id.ll_1_ie_pm25_background);

        mFormaldehydeLevel = (TextView) mView.findViewById(R.id.tv_1_ie_clean_jiaquan_level);
        mFormaldehydeValue = (TextView) mView.findViewById(R.id.tv_1_ie_clean_jiaquan_value);
        mFormaldehydeBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_formaldehyde_background);
    }

    private void initMembers(){


        mPm25Background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE,Constant.GAS_PM25);
                intent.putExtra(Constant.GAS_DETAIL_VALUE,mPm25Value.getText().toString().trim()+"mg/m³");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL,mPm25Level.getText().toString().trim());
                startActivity(intent);
            }
        });

        mFormaldehydeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE,Constant.GAS_JIAQUAN);
                intent.putExtra(Constant.GAS_DETAIL_VALUE,mFormaldehydeValue.getText().toString().trim()+"ppm");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL,mFormaldehydeLevel.getText().toString().trim());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

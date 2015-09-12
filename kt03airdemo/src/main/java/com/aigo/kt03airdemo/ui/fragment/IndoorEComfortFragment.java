package com.aigo.kt03airdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class IndoorEComfortFragment extends Fragment {
    private View mView;

    private TextView mTempLevel;
    private TextView mTempValue;
    private LinearLayout mTempBackground;

    private TextView mHumidityLevel;
    private TextView mHumidityValue;
    private LinearLayout mHumidityBackground;
    private AirIndex mAirIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_ie_comfort, null);

        initView();
        initData();
        initMembers();
        return mView;
    }

    public void setData(AirIndex airIndex){
        mAirIndex = airIndex;
    }

    public void initData(){

        mTempValue.setText(mAirIndex.getTemperature()+"");
        mHumidityValue.setText(mAirIndex.getHumidity()+"");
        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mTempLevel.setText(airQuality.getTemperature());
        mHumidityLevel.setText(airQuality.getHumidity());

    }

    private void initView() {
        mTempLevel = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_temp_level);
        mTempValue = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_temp_value);
        mTempBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_comfort_temp_background);

        mHumidityLevel = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_humidity_level);
        mHumidityValue = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_humidity_value);
        mHumidityBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_comfort_humidity_background);
    }

    private void initMembers() {


        mTempBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_WENDU);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, mAirIndex.getTemperature()+"℃");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mTempLevel.getText().toString().trim());
                startActivity(intent);
            }
        });

        mHumidityBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_SHIDU);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, mAirIndex.getHumidity()+"%");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mHumidityLevel.getText().toString().trim());
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}

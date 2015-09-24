package com.aigo.kt03airdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aigo.kt03airdemo.R;
import com.aigo.kt03airdemo.business.KT03Adapter;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.business.ui.AirQuality;
import com.aigo.kt03airdemo.ui.GasDetailActivity;
import com.aigo.kt03airdemo.ui.util.Constant;


public class IndoorEComfortFragment extends Fragment {
    private static final String TAG = IndoorEComfortFragment.class.getSimpleName();
    private View mView;

    private TextView mTempLevel;
    private TextView mTempValue;
    private LinearLayout mTempBackground;

    private TextView mHumidityLevel;
    private TextView mHumidityValue;
    private LinearLayout mHumidityBackground;
    private AirIndex mAirIndex;

    private Button mBtn;
    private Button mBtn2;

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

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "22222");
        initView();
        initData();
        initMembers();
    }

    public void initData(){
        Log.d(TAG, "getTemperature=" + mAirIndex.getTemperature());
        Log.d(TAG, "getHumidity=" + mAirIndex.getHumidity());
        mTempValue.setText(mAirIndex.getTemperature()+"");
        mHumidityValue.setText(mAirIndex.getHumidity()+"");
        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mTempLevel.setText(airQuality.getTemperature());
        mHumidityLevel.setText(airQuality.getHumidity());

        if(!airQuality.getTemperature().equals("舒适"))
        {
            mBtn.setVisibility(View.VISIBLE);
        }else{
            mBtn.setVisibility(View.INVISIBLE);
        }

        if(!airQuality.getHumidity().equals("适宜")  )
        {
            mBtn2.setVisibility(View.VISIBLE);
        }else{
            mBtn2.setVisibility(View.INVISIBLE);
        }

    }

    private void initView() {
        mTempLevel = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_temp_level);
        mTempValue = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_temp_value);
        mTempBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_comfort_temp_background);

        mHumidityLevel = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_humidity_level);
        mHumidityValue = (TextView) mView.findViewById(R.id.tv_1_ie_comfort_humidity_value);
        mHumidityBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_comfort_humidity_background);

        mBtn = (Button)mView.findViewById(R.id.btn);
        mBtn2 = (Button)mView.findViewById(R.id.btn2);
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

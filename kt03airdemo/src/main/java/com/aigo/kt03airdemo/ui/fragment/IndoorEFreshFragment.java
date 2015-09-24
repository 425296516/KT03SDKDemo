package com.aigo.kt03airdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.aigo.kt03airdemo.ui.util.UiUtil;


public class IndoorEFreshFragment extends Fragment {
    private static final String TAG = IndoorEFreshFragment.class.getSimpleName();
    private View mView;

    private TextView mVOCLevel;
    private TextView mVOCValue;
    private LinearLayout mVOCBackground;

    private TextView mCO2Level;
    private TextView mCO2Value;
    private LinearLayout mCO2Background;
    private AirIndex mAirIndex;

    private Button mBtn;
    private Button mBtn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_ie_fresh, null);
        initView();
        initData();
        initMembers();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "33333");
        initView();
        initData();
        initMembers();
    }

    public void setData(AirIndex airIndex) {
        mAirIndex = airIndex;
    }

    public void initData() {
        Log.d(TAG, "getVoc=" + UiUtil.getVoc(mAirIndex.getVoc()));
        Log.d(TAG, "getCo2=" + mAirIndex.getCo2());
        mVOCValue.setText(UiUtil.getVoc(mAirIndex.getVoc()) + "");
        mCO2Value.setText(mAirIndex.getCo2() + "");

        if (Float.parseFloat(mAirIndex.getVoc()) >= 1) {
            mBtn.setVisibility(View.VISIBLE);
        } else {
            mBtn.setVisibility(View.INVISIBLE);
        }

        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mVOCLevel.setText(airQuality.getVoc());
        mCO2Level.setText(airQuality.getCo2());

        if (Float.parseFloat(mAirIndex.getCo2()) > 600) {
            mBtn2.setVisibility(View.VISIBLE);
        } else {
            mBtn2.setVisibility(View.INVISIBLE);
        }

    }

    private void initView() {
        mVOCLevel = (TextView) mView.findViewById(R.id.tv_1_ie_fresh_voc_level);
        mVOCValue = (TextView) mView.findViewById(R.id.tv_1_ie_fresh_voc_value);
        mVOCBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_fresh_voc_background);
        mBtn = (Button) mView.findViewById(R.id.btn);

        mCO2Level = (TextView) mView.findViewById(R.id.tv_1_ie_fresh_co2_level);
        mCO2Value = (TextView) mView.findViewById(R.id.tv_1_ie_fresh_co2_value);
        mCO2Background = (LinearLayout) mView.findViewById(R.id.ll_1_ie_fresh_co2_background);
        mBtn2 = (Button) mView.findViewById(R.id.btn2);
    }

    private void initMembers() {


        mVOCBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_VOC);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, UiUtil.getVoc(mAirIndex.getVoc()));
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mVOCLevel.getText().toString().trim());
                startActivity(intent);
            }
        });

        mCO2Background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_CO2);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, mAirIndex.getCo2() + "ppm");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mCO2Level.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}
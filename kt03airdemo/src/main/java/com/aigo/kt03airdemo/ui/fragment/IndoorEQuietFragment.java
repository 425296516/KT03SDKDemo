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


public class IndoorEQuietFragment extends Fragment {
    private View mView;

    private TextView mNoiseLevel;
    private TextView mNoiseValue;
    private LinearLayout mNoiseBackground;
    private AirIndex mAirIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_ie_quiet,null);

        initView();
        initData();
        initMembers();
        return mView;
    }

    public void setData(AirIndex airIndex){
        mAirIndex = airIndex;
    }

    public void initData(){

        mNoiseValue.setText(mAirIndex.getNoise()+"");
        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mNoiseLevel.setText(airQuality.getNoise());

    }

    private void initView() {
        mNoiseLevel = (TextView) mView.findViewById(R.id.tv_1_ie_quiet_noise_level);
        mNoiseValue = (TextView) mView.findViewById(R.id.tv_1_ie_quiet_noise_value);
        mNoiseBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_quiet_noise_background);


    }

    private void initMembers() {


        mNoiseBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_ZAOYIN);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, mAirIndex.getNoise()+"DB");
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mNoiseLevel.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

}

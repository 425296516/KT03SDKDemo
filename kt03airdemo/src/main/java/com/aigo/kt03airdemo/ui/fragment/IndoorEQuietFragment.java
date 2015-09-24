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


public class IndoorEQuietFragment extends Fragment {
    private static final String TAG = IndoorEQuietFragment.class.getSimpleName();
    private View mView;

    private TextView mNoiseLevel;
    private TextView mNoiseValue;
    private LinearLayout mNoiseBackground;
    private AirIndex mAirIndex;
    private Button mBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_1_ie_quiet,null);

        initView();
        initData();
        initMembers();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "44444");
        initView();
        initData();
        initMembers();
    }

    public void setData(AirIndex airIndex){
        mAirIndex = airIndex;
    }

    public void initData(){

        mNoiseValue.setText(UiUtil.getNoise(mAirIndex.getNoise())+"");
        AirQuality airQuality = KT03Adapter.getInstance().getAirIndexToAirQuality(mAirIndex);
        mNoiseLevel.setText(airQuality.getNoise());
        if(Integer.parseInt(mAirIndex.getNoise())>=3)
        {
            mBtn.setVisibility(View.VISIBLE);
        }else{
            mBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        mNoiseLevel = (TextView) mView.findViewById(R.id.tv_1_ie_quiet_noise_level);
        mNoiseValue = (TextView) mView.findViewById(R.id.tv_1_ie_quiet_noise_value);
        mBtn = (Button)mView.findViewById(R.id.btn);

        mNoiseBackground = (LinearLayout) mView.findViewById(R.id.ll_1_ie_quiet_noise_background);

    }

    private void initMembers() {

        mNoiseBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GasDetailActivity.class);
                intent.putExtra(Constant.GAS_DETAIL_TYPE, Constant.GAS_ZAOYIN);
                intent.putExtra(Constant.GAS_DETAIL_VALUE, UiUtil.getNoise(mAirIndex.getNoise()));
                intent.putExtra(Constant.GAS_DETAIL_LEVEL, mNoiseLevel.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

}

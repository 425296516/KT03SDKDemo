package com.aigo.kt03airdemo.business.task;


import com.aigo.kt03airdemo.business.KT03Adapter;
import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.kt03airdemo.business.kt03.KT03AirIndexObject;
import com.aigo.kt03airdemo.business.ui.AirIndex;

import java.util.TimerTask;

/**
 * Created by zhangcirui on 15/7/18.
 */
public class TimerAirIndexTask extends TimerTask {


    private KT03AirModule.OnPostListener mListener;

    public TimerAirIndexTask(KT03AirModule.OnPostListener listener){
        mListener = listener;
    }

    @Override
    public void run() {

        GetAirIndexTask task = new GetAirIndexTask() {

            @Override
            protected void onSuccess(KT03AirIndexObject result) throws Exception {
                if(result != null && result.getData() != null && !result.getData().getVoc().isEmpty()
                        && !result.getData().getCo2().isEmpty()&& !result.getData().getFormadehyde().isEmpty()&& !result.getData().getHumidity().isEmpty()
                        && !result.getData().getNoise().isEmpty()&& !result.getData().getPm25().isEmpty()&& !result.getData().getTemperature().isEmpty()){
                    AirIndex airIndex = KT03Adapter.getInstance().getAirIndex(result);
                    airIndex.setPm25(Float.parseFloat(airIndex.getPm25())/1000+"");
                    mListener.onSuccess(airIndex);
                }
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                mListener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }
}

package com.aigo.kt03airdemo.business.task;

import android.util.Log;

import com.aigo.kt03airdemo.business.kt03.KT03AirIndex;
import com.aigo.kt03airdemo.business.kt03.KT03AirIndexObject;
import com.aigo.kt03airdemo.business.ui.KT03AirInfo;
import com.aigo.kt03airdemo.business.util.Constant;
import com.google.gson.Gson;
import com.goyourfly.base_task.SafeAsyncTask;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class GetAirIndexTask extends SafeAsyncTask<KT03AirIndexObject> {

    private static final String TAG = GetAirIndexTask.class.getSimpleName();
    //http://192.168.3.166/air/getAirIndex.json
    @Override
    public KT03AirIndexObject call() throws Exception {

        try {
            StringBuffer url = new StringBuffer(Constant.URL_KT03 + "/air/getAirIndex.json");
            Log.d(TAG,url.toString());
            Future<String> future = AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpGet(url.toString()), null);
            String value = future.get(Constant.TIME_OUT, TimeUnit.MILLISECONDS);

            KT03AirIndexObject kt03AirIndexObject = new Gson().fromJson(value, KT03AirIndexObject.class);
            Log.d(TAG,kt03AirIndexObject.toString());
            if(kt03AirIndexObject==null || kt03AirIndexObject.getData()==null ||kt03AirIndexObject.getResult() == null){
                KT03AirInfo kt03AirInfo = new Gson().fromJson(value, KT03AirInfo.class);
                kt03AirIndexObject.setResult(kt03AirInfo.getAirinfo().getResult());
                kt03AirIndexObject.setData(kt03AirInfo.getAirinfo().getData());
                kt03AirIndexObject.setPubtime(kt03AirInfo.getAirinfo().getPubtime());
            }

            Log.d(TAG,kt03AirIndexObject.toString());

            return kt03AirIndexObject;

        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}

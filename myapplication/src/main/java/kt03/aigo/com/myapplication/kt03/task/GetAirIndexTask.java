package kt03.aigo.com.myapplication.kt03.task;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.google.gson.Gson;
import com.goyourfly.base_task.SafeAsyncTask;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;

import kt03.aigo.com.myapplication.kt03.bean.AirIndexObject;
import kt03.aigo.com.myapplication.kt03.util.Constant;


/**
 * Created by zhangcirui on 15/7/15.
 */
public class GetAirIndexTask extends SafeAsyncTask<AirIndexObject> {

    private static final String TAG = GetAirIndexTask.class.getSimpleName();

    @Override
    public AirIndexObject call() throws Exception {

        try {
            StringBuffer url = new StringBuffer(Constant.URL_KT03 + "/air/getAirIndex.json");
            Future<String> future = AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpGet(url.toString()), null);
            String value = future.get(Constant.TIME_OUT, TimeUnit.MILLISECONDS);

            return new Gson().fromJson(value, AirIndexObject.class);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}

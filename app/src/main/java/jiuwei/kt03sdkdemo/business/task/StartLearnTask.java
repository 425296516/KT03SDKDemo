package jiuwei.kt03sdkdemo.business.task;

import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import jiuwei.kt03sdkdemo.business.obj.ResultObject;
import jiuwei.kt03sdkdemo.business.util.Constant;
import jiuwei.kt03sdkdemo.business.util.SafeAsyncTask;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class StartLearnTask extends SafeAsyncTask<ResultObject> {

    private static final String TAG = StartLearnTask.class.getSimpleName();

    @Override
    public ResultObject call() throws Exception {

        try {
            StringBuffer url = new StringBuffer(Constant.URL_KT03 + "/send/startLearn.json");
            Future<String> future = AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpGet(url.toString()), null);
            String value = future.get(Constant.TIME_OUT, TimeUnit.MILLISECONDS);

            return new Gson().fromJson(value, ResultObject.class);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}
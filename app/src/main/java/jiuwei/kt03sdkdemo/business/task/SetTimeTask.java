package jiuwei.kt03sdkdemo.business.task;

import android.util.Log;

import com.google.gson.Gson;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;

import java.util.concurrent.TimeUnit;

import jiuwei.kt03sdkdemo.business.obj.ResultObject;
import jiuwei.kt03sdkdemo.business.util.Constant;
import jiuwei.kt03sdkdemo.business.util.SafeAsyncTask;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class SetTimeTask extends SafeAsyncTask<ResultObject> {

    private static final String TAG = SetTimeTask.class.getSimpleName();
    private long mTime;

    public SetTimeTask(long time) {
        mTime = time;
    }

    @Override
    public ResultObject call() throws Exception {

        StringBuffer url = new StringBuffer(Constant.URL_KT03 + "/ir/setTime.json");
        url.append("?timestamp=").append(mTime);
        Log.d(TAG,url.toString());

        try {
            Future<String> future = AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpGet(url.toString()), null);
            String value = future.get(Constant.TIME_OUT, TimeUnit.MILLISECONDS);
            return new Gson().fromJson(value, ResultObject.class);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }

}
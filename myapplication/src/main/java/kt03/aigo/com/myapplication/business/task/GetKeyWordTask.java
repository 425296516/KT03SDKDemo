package kt03.aigo.com.myapplication.business.task;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.goyourfly.base_task.SafeAsyncTask;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;

/**
 * Created by zhangcirui on 15/8/26.
 */
public class GetKeyWordTask extends SafeAsyncTask<List<Remote>> {

    private static final String TAG = GetKeyWordTask.class.getSimpleName();

    private String keyWords;
    private List<Remote> mRemotes = new ArrayList<>();

    public GetKeyWordTask(String keyWord) {
        this.keyWords = keyWord;
    }

    @Override
    public List<Remote> call() throws Exception {

        Gson gson = new Gson();
        String getCmdBase64 = Base64.encodeToString(keyWords.getBytes(), Base64.DEFAULT);

        String url = Globals.GETSERVERKEYWOARDS + getCmdBase64;
        Log.d(TAG, "keyword=" + url);
        try {
            Object[][] searchRemote = gson.fromJson(
                    HttpRequest.sendGet(url), new TypeToken<Object[][]>() {
                    }.getType());
            for (Object[] object : searchRemote) {
                Remote remote = new Remote();
                Double temp = (Double) object[0];
                int tempInt = (new Double(temp)).intValue();
                remote.setId(String.valueOf(tempInt));
                temp = (Double) object[1];
                tempInt = (new Double(temp)).intValue();
                remote.setType(tempInt);
                Brand brand = new Brand();
                brand.setBrand((String) object[2]);
                brand.setBrand_tra((String) object[3]);
                brand.setSortLetters((String) object[2]);
                remote.setBrand(brand);
                remote.setModel((String) object[4]);
                remote.setName((String) object[4]);
                Log.d(TAG, new Gson().toJson(remote));
                mRemotes.add(remote);
            }

        } catch (JsonSyntaxException e) {

            e.printStackTrace();
        }

        return mRemotes;

    }
}

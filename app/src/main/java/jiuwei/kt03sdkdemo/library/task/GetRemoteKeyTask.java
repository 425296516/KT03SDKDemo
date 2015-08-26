package jiuwei.kt03sdkdemo.library.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyourfly.base_task.SafeAsyncTask;

import java.util.ArrayList;
import java.util.List;

import jiuwei.kt03sdkdemo.library.bean.IRCode;
import jiuwei.kt03sdkdemo.library.bean.IRKey;
import jiuwei.kt03sdkdemo.library.bean.Infrared;
import jiuwei.kt03sdkdemo.library.util.Constant;
import jiuwei.kt03sdkdemo.library.util.HttpRequest;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class GetRemoteKeyTask extends SafeAsyncTask<KeyList> {

    private static final String TAG = GetAirConditionerCodeListTask.class.getSimpleName();
    private int mIdModelSearch;

    public GetRemoteKeyTask(int idModelSearch) {
        this.mIdModelSearch = idModelSearch;
    }

    @Override
    public KeyList call() throws Exception {

        Gson gson = new Gson();
        String cmd = Constant.GETSERVERREMOTEKEY
                + mIdModelSearch;

        List<IRKey> irKeys = new ArrayList<IRKey>();
        KeyList keyList = new KeyList();

        Object[][] comIrKeys = gson.fromJson(
                HttpRequest.sendGet(cmd),
                new TypeToken<Object[][]>() {
                }.getType());

        for (Object[] object : comIrKeys) {
            IRKey irKey = new IRKey();
            irKey.setName((String) object[0]);

            List<Infrared> infs = new ArrayList<Infrared>();
            IRCode ir = new IRCode((String) object[1]);
            Infrared inf = new Infrared(ir);

            infs.add(inf);

            ir = new IRCode((String) object[2]);
            inf = new Infrared(ir);
            infs.add(inf);
            irKey.setProtocol(0);
            irKey.setInfrareds(infs);

            irKeys.add(irKey);
        }
        if (irKeys != null && irKeys.size() > 0) {
            keyList.setIrKeys(irKeys);
            return keyList;
        } else {
            return keyList;
        }
    }
}
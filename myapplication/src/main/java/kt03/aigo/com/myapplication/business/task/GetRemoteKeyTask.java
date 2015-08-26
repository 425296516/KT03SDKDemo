package kt03.aigo.com.myapplication.business.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyourfly.base_task.SafeAsyncTask;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.IRKeyList;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class GetRemoteKeyTask extends SafeAsyncTask<IRKeyList> {

    private static final String TAG = GetAirConditionerCodeListTask.class.getSimpleName();
    private int mIdModelSearch;

    public GetRemoteKeyTask(int idModelSearch) {
        this.mIdModelSearch = idModelSearch;
    }

    @Override
    public IRKeyList call() throws Exception {

        Gson gson = new Gson();
        String cmd = Globals.GETSERVERREMOTEKEY
                + mIdModelSearch;

        List<IRKey> irKeys = new ArrayList<IRKey>();
        IRKeyList keyList = new IRKeyList();

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
            return null;
        }
    }
}
package kt03.aigo.com.myapplication.business.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyourfly.base_task.SafeAsyncTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.ModelNum;
import kt03.aigo.com.myapplication.business.bean.ModelNumObject;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class GetAirConditionerCodeListTask extends SafeAsyncTask<ModelNumObject> {

    private static final String TAG = GetAirConditionerCodeListTask.class.getSimpleName();
    private int mIdDevice;
    private int mIdBrand;

    public GetAirConditionerCodeListTask(int idDevice, int idBrand) {
        this.mIdDevice = idDevice;
        this.mIdBrand = idBrand;
    }


    @Override
    public ModelNumObject call() throws Exception {

        ModelNumObject modelNumObject = new ModelNumObject();
        List<ModelNum> modelSearch = new ArrayList<ModelNum>();

        Gson gson = new Gson();
        String cmd = Globals.GETSERVERSEARCHREMOTE
                + mIdDevice + "/"
                + mIdBrand;

        Map<Integer, List<Object[]>> searchRemoteKeys = gson.fromJson(HttpRequest.sendGet(cmd), new TypeToken<Map<Integer, List<Object[]>>>() {
        }.getType());

        Iterator<Map.Entry<Integer, List<Object[]>>> it = searchRemoteKeys.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Object[]>> entry = it.next();
            ModelNum ms = new ModelNum(entry.getKey());
            Log.d(TAG, "entry.getKey()=" + entry.getKey());
            List<Object[]> values = entry.getValue();
            List<IRKey> irkeys = new ArrayList<IRKey>();
            for (int i = 0; i < values.size(); i++) {
                Object[] obj = values.get(i);
                IRKey irkey = new IRKey();
                irkey.setName((String) obj[0]);
                IRCode ir = new IRCode((String) obj[1]);

                Log.d(TAG,"name="+(String)obj[0]+"key="+(String)obj[1]);

                Infrared inf = new Infrared(ir);
                List<Infrared> infrareds = new ArrayList<Infrared>();
                infrareds.add(inf);
                irkey.setInfrareds(infrareds);
                irkeys.add(irkey);
            }
            ms.setKeys(irkeys);
            modelSearch.add(ms);

        }

        modelNumObject.setModelNumList(modelSearch);

        return modelNumObject;

    }
}

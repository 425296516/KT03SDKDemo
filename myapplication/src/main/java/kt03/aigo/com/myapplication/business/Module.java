package kt03.aigo.com.myapplication.business;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import kt03.aigo.com.myapplication.business.bean.BrandList;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.db.SPManager;
import kt03.aigo.com.myapplication.business.task.GetAirConditionerCodeListTask;
import kt03.aigo.com.myapplication.business.task.GetBrandListTask;
import kt03.aigo.com.myapplication.business.task.GetKeyWordTask;
import kt03.aigo.com.myapplication.business.task.GetRemoteKeyTask;
import kt03.aigo.com.myapplication.business.bean.IRKeyList;
import kt03.aigo.com.myapplication.business.bean.ModelNumObject;
import kt03.aigo.com.myapplication.business.util.Globals;

/**
 * Created by zhangcirui on 15/8/18.
 */
public class Module {

    private static final String TAG = Module.class.getSimpleName();
    private static Module module;
    private Context mContext;

    public interface OnPostListener<T> {
        public void onSuccess(T result);

        public void onFailure(String err);
    }

    public void init(Context context) {
        mContext = context;
    }

    public static Module getInstance() {
        if (module == null) {
            module = new Module();
        }
        return module;
    }


    public void getBrandList(final OnPostListener listener) {

        BrandList brandList = new Gson().fromJson(SPManager.getInstance().getBrandList(),BrandList.class);
        if(brandList != null && brandList.getBrandList()!= null && brandList.getBrandList().size()>0){

            listener.onSuccess(brandList);

        }else {

            GetBrandListTask getBrandListTask = new GetBrandListTask(Globals.AIR_CONDITIONER) {

                @Override
                protected void onSuccess(BrandList brandList) throws Exception {
                    listener.onSuccess(brandList);

                    SPManager.getInstance().setBrandList(new Gson().toJson(brandList));

                    super.onSuccess(brandList);
                }

                @Override
                protected void onException(Exception e) throws RuntimeException {
                    super.onException(e);
                }
            };

            getBrandListTask.execute();
        }
    }


    public void getModelNumList(final int brandId,final OnPostListener<ModelNumObject> listener) {

        ModelNumObject modelNumObject = new Gson().fromJson(SPManager.getInstance().getModelNumListInfo(brandId), ModelNumObject.class);

        if (modelNumObject != null && modelNumObject.getModelNumList() != null) {

            listener.onSuccess(modelNumObject);

        } else {

            GetAirConditionerCodeListTask task = new GetAirConditionerCodeListTask(5, brandId) {

                @Override
                protected void onSuccess(ModelNumObject modelNumObject) throws Exception {
                    super.onSuccess(modelNumObject);
                    Log.d(TAG, modelNumObject.toString() + "");
                    listener.onSuccess(modelNumObject);
                    SPManager.getInstance().setModelNumListInfo(brandId,new Gson().toJson(modelNumObject));

                }

                @Override
                protected void onException(Exception e) throws RuntimeException {
                    super.onException(e);
                    listener.onFailure(e.toString());
                }
            };

            task.execute();
        }

    }


    public void getRemoteKey(final int idModelSearch ,final OnPostListener<IRKeyList> listener) {

        IRKeyList keyList = new Gson().fromJson(SPManager.getInstance().getRemoteKey(idModelSearch), IRKeyList.class);

        if (keyList != null && keyList.getIrKeys().size() != 0) {

            listener.onSuccess(keyList);

        } else {

            GetRemoteKeyTask task = new GetRemoteKeyTask(idModelSearch) {

                @Override
                protected void onSuccess(IRKeyList keyList) throws Exception {
                    super.onSuccess(keyList);

                    listener.onSuccess(keyList);
                    SPManager.getInstance().setRemoteKey(idModelSearch,new Gson().toJson(keyList));
                }

                @Override
                protected void onException(Exception e) throws RuntimeException {
                    super.onException(e);

                    listener.onFailure(e.toString());
                }
            };

            task.execute();

        }
    }


    public void getKeyWord(final String keyWord,final OnPostListener<List<Remote>> listener){

        GetKeyWordTask task = new GetKeyWordTask(keyWord){

            @Override
            protected void onSuccess(List<Remote> remotes) throws Exception {
                super.onSuccess(remotes);

                listener.onSuccess(remotes);
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);

                listener.onFailure(e.toString());
            }

        };
        task.execute();
    }


}

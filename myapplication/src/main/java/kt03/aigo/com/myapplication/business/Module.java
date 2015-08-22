package kt03.aigo.com.myapplication.business;

import java.util.List;
import java.util.Timer;

import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.task.getBrandListTask;
/**
 * Created by zhangcirui on 15/8/18.
 */
public class Module {


    private static final String TAG = Module.class.getSimpleName();
    private static Module module;
    private Timer timer;

    public interface OnPostListener<T> {
        public void onSuccess(T result);

        public void onFailure(String err);
    }


    public static Module getInstance() {
        if (module == null) {
            module = new Module();
        }
        return module;
    }


    public void getBrandList(final OnPostListener listener){


        getBrandListTask getBrandListTask = new getBrandListTask(){

            @Override
            protected void onSuccess(List<Brand> brand) throws Exception {
                listener.onSuccess(brand);
                super.onSuccess(brand);
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
            }
        };

        getBrandListTask.execute();



    }


}

package kt03.aigo.com.myapplication.kt03.task;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.goyourfly.base_task.SafeAsyncTask;

import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;

/**
 * Created by zhangcirui on 15/8/18.
 */
public class getBrandListTask extends SafeAsyncTask<List<Brand>> {

    private static final String TAG = getBrandListTask.class.getSimpleName();

    @Override
    public List<Brand> call() throws Exception {

        try {
  

            List<Brand> brands = new ArrayList<Brand>();
            Gson gson = new Gson();

            Object[][] comBrands = gson.fromJson(
                    HttpRequest.sendGet(Globals.GETSERVERBRAND + "5"),
                    new TypeToken<Object[][]>() {
                    }.getType());

            for (Object[] object : comBrands) {
                Brand brand = new Brand();
                brand.setId(Double.valueOf((Double) object[0]).intValue());
                brand.setBrand((String) object[1]);
                if (Globals.LocalLanguage == 0) {
                    brand.setBrand_tra((String) object[3]);
                } else {
                    brand.setBrand_tra((String) object[5]);
                }
                brand.setSortLetters((String) object[1]);
                brands.add(brand);
            }

            Log.d(TAG,brands.toString());
            return brands;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}

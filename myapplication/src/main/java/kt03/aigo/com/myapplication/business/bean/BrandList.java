package kt03.aigo.com.myapplication.business.bean;

import java.util.List;

/**
 * Created by zhangcirui on 15/8/25.
 */
public class BrandList {

    private List<Brand> brandList;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    @Override
    public String toString() {
        return "BrandList{" +
                "brandList=" + brandList +
                '}';
    }
}

package kt03.aigo.com.myapplication.business.task;

import java.util.List;

import kt03.aigo.com.myapplication.business.bean.ModelNum;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class ModelNumObject {

    private List<ModelNum> modelNumList;

    public List<ModelNum> getModelNumList() {
        return modelNumList;
    }

    public void setModelNumList(List<ModelNum> modelNumList) {
        this.modelNumList = modelNumList;
    }

    @Override
    public String toString() {
        return "ModelNumObject{" +
                "modelNumList=" + modelNumList +
                '}';
    }
}

package kt03.aigo.com.myapplication.business.bean;

import java.util.List;
import java.util.Map;

import kt03.aigo.com.myapplication.business.bean.IRKey;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class IRKeyList {

    public List<IRKey> irKeys;

    public List<IRKey> getIrKeys() {
        return irKeys;
    }

    public void setIrKeys(List<IRKey> irKeys) {
        this.irKeys = irKeys;
    }

    @Override
    public String toString() {
        return "KeyList{" +
                "irKeys=" + irKeys +
                '}';
    }
}

package jiuwei.kt03sdkdemo.library.task;

import java.util.List;

import jiuwei.kt03sdkdemo.library.bean.IRKey;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class KeyList {

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

package com.aigo.kt03airdemo.business.kt03;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class KT03ResultObject {
    
    private KT03Result result;

    public KT03Result getResult() {
        return result;
    }

    public void setResult(KT03Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "result=" + result +
                '}';
    }
}

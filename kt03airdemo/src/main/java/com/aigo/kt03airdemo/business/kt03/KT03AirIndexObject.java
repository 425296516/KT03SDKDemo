package com.aigo.kt03airdemo.business.kt03;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class KT03AirIndexObject {

    private KT03ResultObject result;

    private KT03AirIndex data;

    private long pubtime;

    public KT03ResultObject getResult() {
        return result;
    }

    public void setResult(KT03ResultObject result) {
        this.result = result;
    }

    public KT03AirIndex getData() {
        return data;
    }

    public void setData(KT03AirIndex data) {
        this.data = data;
    }

    public long getPubtime() {
        return pubtime;
    }

    public void setPubtime(long pubtime) {
        this.pubtime = pubtime;
    }

    @Override
    public String toString() {
        return "AirIndexObject{" +
                "result=" + result +
                ", data=" + data +
                ", pubtime=" + pubtime +
                '}';
    }
}

package jiuwei.kt03sdkdemo.business.obj;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class AirIndexObject {

    private ResultObject result;

    private AirIndex data;

    private long pubtime;

    public ResultObject getResult() {
        return result;
    }

    public void setResult(ResultObject result) {
        this.result = result;
    }

    public AirIndex getData() {
        return data;
    }

    public void setData(AirIndex data) {
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

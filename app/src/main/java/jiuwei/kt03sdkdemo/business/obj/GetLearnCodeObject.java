package jiuwei.kt03sdkdemo.business.obj;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class GetLearnCodeObject {

    private Result result;

    private String code;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GetLearnCodeObject{" +
                "result=" + result +
                ", code='" + code + '\'' +
                '}';
    }
}

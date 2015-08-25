package kt03.aigo.com.myapplication.kt03.bean;

/**
 * Created by zhangcirui on 15/7/15.
 */
public class ResultObject {
    
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "result=" + result +
                '}';
    }
}

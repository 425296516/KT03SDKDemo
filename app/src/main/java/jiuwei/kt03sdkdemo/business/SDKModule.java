package jiuwei.kt03sdkdemo.business;


import android.os.Looper;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import jiuwei.kt03sdkdemo.business.obj.AirIndexObject;
import jiuwei.kt03sdkdemo.business.obj.GetLearnCodeObject;
import jiuwei.kt03sdkdemo.business.obj.ResultObject;
import jiuwei.kt03sdkdemo.business.task.GetAirIndexTask;
import jiuwei.kt03sdkdemo.business.task.GetLearnCodeTask;
import jiuwei.kt03sdkdemo.business.task.SendIRCodeTask;
import jiuwei.kt03sdkdemo.business.task.SetTimeTask;
import jiuwei.kt03sdkdemo.business.task.StartLearnTask;
import jiuwei.kt03sdkdemo.business.task.StopLearnTask;

/**
 * Created by zhangcirui on 15/7/14.
 */
public class SDKModule {

    private static final String TAG = SDKModule.class.getSimpleName();
    private static SDKModule module;
    private Timer timer;

    public interface OnPostListener<T> {
        public void onSuccess(T result);

        public void onFailure(String err);
    }


    public static SDKModule getInstance() {
        if (module == null) {
            module = new SDKModule();
        }
        return module;
    }


    public void setTime(long time, final OnPostListener listener) {

        SetTimeTask task = new SetTimeTask(time) {

            @Override
            protected void onSuccess(ResultObject result) throws Exception {
                if (result != null)
                    listener.onSuccess(result);
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();

    }


    public void getAirIndex(final OnPostListener listener) {


        GetAirIndexTask task = new GetAirIndexTask() {

            @Override
            protected void onSuccess(AirIndexObject o) throws Exception {
                if (o != null)
                    listener.onSuccess(o.toString());
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }


    public void getLearnCode(final OnPostListener listener) {


        GetLearnCodeTask task = new GetLearnCodeTask() {

            @Override
            protected void onSuccess(GetLearnCodeObject o) throws Exception {
                if (o != null)
                    listener.onSuccess(o);
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }

    public void startLearn(final OnPostListener listener) {


        StartLearnTask task = new StartLearnTask() {

            @Override
            protected void onSuccess(ResultObject o) throws Exception {

                if (o != null)
                    listener.onSuccess(o.getResult().getRet());

            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }

    public void setLearnModel(final OnPostListener listener) {
        // mTime = 0;
        StartLearnTask task = new StartLearnTask() {

            @Override
            protected void onSuccess(ResultObject result) throws Exception {

                    timer = new Timer();
                    NewTimerTask timerTask = new NewTimerTask(new OnPostListener<GetLearnCodeObject>() {
                        @Override
                        public void onSuccess(final GetLearnCodeObject result) {

                            SDKModule.getInstance().stopLearn(new SDKModule.OnPostListener<Integer>() {
                                @Override
                                public void onSuccess(Integer ret) {
                                    listener.onSuccess(result.getCode());
                                    timer = null;
                                }

                                @Override
                                public void onFailure(String err) {
                                    listener.onFailure(err);
                                    timer = null;
                                }
                            });
                        }

                        @Override
                        public void onFailure(String err) {
                            Log.d(TAG, err);
                            Looper.prepare();
                            listener.onFailure(err);
                            Looper.loop();
                        }
                    });
                    //程序运行后立刻执行任务，每隔100ms执行一次
                    timer.schedule(timerTask, 0, 5000);
                }


            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }

    public class NewTimerTask extends TimerTask {
        private int mTime = 0;
        private OnPostListener mListener;

        public NewTimerTask(OnPostListener listener) {
            mListener = listener;
        }

        @Override
        public void run() {
            if (mTime < 10) {
                mTime++;
                Log.d(TAG, "time=" + mTime + "");
                SDKModule.getInstance().getLearnCode(new SDKModule.OnPostListener<GetLearnCodeObject>() {
                    @Override
                    public void onSuccess(final GetLearnCodeObject result) {

                        if (result.getResult().getRet() == 1) {

                        } else if (result.getResult().getRet() == 0) {
                            timer.cancel();
                            mListener.onSuccess(result);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        mListener.onFailure(err);
                    }
                });
            } else {
                if (timer != null) {
                    mListener.onFailure("获取超时");
                    timer.cancel();
                    timer = null;
                }
            }
        }

    }

    public void stopLearn(final OnPostListener listener) {


        StopLearnTask task = new StopLearnTask() {

            @Override
            protected void onSuccess(ResultObject o) throws Exception {
                if (o != null)
                    listener.onSuccess(o.getResult().getRet());
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();
    }

    public void sendIRCode(String code, final OnPostListener listener) {


        SendIRCodeTask task = new SendIRCodeTask(code) {

            @Override
            protected void onSuccess(ResultObject o) throws Exception {
                if (o != null)
                    listener.onSuccess(o.getResult().getRet());
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                listener.onFailure(e.toString());
                super.onException(e);
            }
        };
        task.execute();


    }

}

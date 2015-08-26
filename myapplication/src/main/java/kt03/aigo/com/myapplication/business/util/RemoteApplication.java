package kt03.aigo.com.myapplication.business.util;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;

public class RemoteApplication extends Application {
    public static RemoteApplication mAppContext;

    private List<Activity> activityList = new ArrayList<Activity>();

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = this;
    }

    public static RemoteApplication getInstance() {
        return mAppContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AirRemoteStateMananger.getInstance(getApplicationContext()).flush();
    }

    /**
     * 获取app context
     */
    public static Context getAppContext() {
        return mAppContext;
    }

}

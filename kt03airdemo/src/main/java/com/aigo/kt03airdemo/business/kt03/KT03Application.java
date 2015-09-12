package com.aigo.kt03airdemo.business.kt03;

import android.app.Application;
import android.content.Context;

import com.aigo.kt03airdemo.business.KT03AirModule;
import com.aigo.usermodule.business.UserModule;


/**
 * Created by zhangcirui on 15/7/18.
 */
public class KT03Application extends Application {

    public static KT03Application mAppContext;

    @Override
    public void onCreate() {

        UserModule.getInstance().init(this);
        UserModule.initKeys("", "", "829540725", "705229ff42e060f857f1d1ad413a8308", "https://api.weibo.com/oauth2/default.html");
        KT03AirModule.getInstance().init(getApplicationContext());

        mAppContext = this;



    }

    public static KT03Application getInstance() {
        return mAppContext;
    }


    /**
     * 获取app context
     */
    public static Context getAppContext() {
        return mAppContext;
    }

}

package kt03.aigo.com.myapplication.business.util;


import java.util.ArrayList;
import java.util.List;




import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;

public class RemoteApplication extends Application
{

	/**是否是测试模式*/
	public final static boolean TEST_MODE = true;
	
	
	public static RemoteApplication mAppContext;
	
	
	 private List<Activity> activityList = new ArrayList<Activity>();
	 
	 
	  
	 public void addActivity(Activity activity)
	    {
	        activityList.add(activity);
	    }
	    // 遍历所有Activity并finish
	    public void quitActivity()
	    {
	        for (Activity activity : activityList)
	        {
	            activity.finish();
	        }
	        activityList.clear();
	       
	    }
	
	
	
	
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		
		mAppContext =this;
//		IrDnaSdkHelper.init(this, license);
	}
	
	
	public static RemoteApplication getInstance() {
        return mAppContext;
   }
	
	@Override
	public void onTerminate()
	{
		// TODO Auto-generated method stub
		super.onTerminate();
		AirRemoteStateMananger.getInstance(getApplicationContext()).flush();
	}
	
	/**获取app context*/
	public static Context getAppContext()
	{
		return mAppContext;
	}
	
	
	public void putIntToPreff(String index, int data) {
		SharedPreferences.Editor localEditor = getSharedPreferences(
				Globals.REMOTE_SHARED_PREFF, 0).edit();
		localEditor.putInt(index, data);
		localEditor.commit();
	}

	public void putStringToPreff(String index, String data) {
		SharedPreferences.Editor localEditor = getSharedPreferences(
				Globals.REMOTE_SHARED_PREFF, 0).edit();
		localEditor.putString(index, data);
		localEditor.commit();
	}

	public int retrieveIntFromPreff(String index) {
		return getSharedPreferences(Globals.REMOTE_SHARED_PREFF, 0).getInt(
				index, 0);
	}

	public String retrieveStringFromPreff(String index) {
		return getSharedPreferences(Globals.REMOTE_SHARED_PREFF, 0).getString(
				index, "");
	}

	public String retrieveStringFromPreff(String index, String data) {
		return getSharedPreferences(Globals.REMOTE_SHARED_PREFF, 0).getString(
				index, data);
	}
}

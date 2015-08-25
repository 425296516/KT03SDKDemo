package kt03.aigo.com.myapplication.business.db;

import android.content.Context;

import com.goyourfly.ln.Ln;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class SPManager {

    public static final String BRAND_LIST = "BRAND_LIST";
    public static final String KEY_LIST = "KEY_LIST";
    public static final String MODEL_NUM_LIST = "MODEL_NUM_LIST";
    public static final String REMOTE_KEY = "REMOTE_KEY";
    //public static final String MASTER_INFO = "MASTER_INFO";
    //public static final String SESSION_SETTING_INFO = "SESSION_SETTING_INFO";


    private static SPManager spMasterManager;

    private static Context mContext;

    public  void init(Context context) {
        mContext = context;
    }

    public static SPManager getInstance(){
        if(spMasterManager == null){
            spMasterManager = new SPManager();
        }
        return spMasterManager;
    }

    public  String getBrandList() {

        return SPreferences.getString(mContext,BRAND_LIST, null);
    }

    public boolean setBrandList(String info) {

        return SPreferences.putStr(mContext, BRAND_LIST, info);
    }

    public String getModelNumListInfo(int brandId){
        return  SPreferences.getString(mContext,MODEL_NUM_LIST+brandId,null);
    }

    public void setModelNumListInfo(int brandId,String info){
        SPreferences.putString(mContext,MODEL_NUM_LIST+brandId,info);
    }

    public String getRemoteKey(int key){
        return  SPreferences.getString(mContext,REMOTE_KEY+key,null);
    }

    public void setRemoteKey(int key,String info){
        SPreferences.putString(mContext,REMOTE_KEY+""+key,info);
    }


  /*  public String getMasterInfo(){
        Ln.d("SPreferences:getMasterInfo:" + SPreferences.getString(mContext, MASTER_INFO, null));
        return  SPreferences.getString(mContext,MASTER_INFO,null);
    }

    public void setMasterInfo(String info){
        SPreferences.putString(mContext,MASTER_INFO,info);
    }

    public void setSessionSetting(String info){
        SPreferences.putString(mContext,SESSION_SETTING_INFO,info);
    }

    public String getSessionSetting(){
        return  SPreferences.getString(mContext,SESSION_SETTING_INFO,null);
    }
*/
}

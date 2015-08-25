package kt03.aigo.com.myapplication.business.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by goyourfly on 14-5-23.
 */
public class SPreferences {
    public static String sPName = "com.aigo.kt03";

    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static boolean putStr(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor.putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        return sharedPreferences.getString(key,defValue);
    }

/*
    public static void putInt(Context context, String key,int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(Context context,String key,int defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        return sharedPreferences.getInt(key,defValue);
    }


    public static void putLong(Context context, String key,long value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    public static long getLong(Context context,String key,long defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        return sharedPreferences.getLong(key,defValue);
    }

    public static void putBoolean(Context context,String key , boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static void putFloat(Context context, String key,float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,value);
        editor.commit();
    }

    public static float getFloat(Context context,String key,float defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        return sharedPreferences.getFloat(key,defValue);
    }
*/

    public static void clear(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void remove(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sPName,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
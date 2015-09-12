package com.aigo.kt03airdemo.business.db;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aigo.kt03airdemo.business.util.Constant;

/**
 * Created by zhangcirui on 15/9/3.
 */
public class DbAirIndexInfoHelper extends SQLiteOpenHelper {


    public static final String TABLE_AIR_INDEX = "TABLE_AIR_INDEX";

    public DbAirIndexInfoHelper(Context context) {
        this(context, Constant.DB_DEVICE_INFO, null, 1);
    }

    public DbAirIndexInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbAirIndexInfoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    /**
     *
     *     private int id;
     private String temperature;
     private String humidity;
     private String noise;
     private String co2;
     private String voc;
     private String pm25;
     private String formadehyde;
     private long pubtime;
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_AIR_INDEX + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pubtime String,"+
                "temperature String," +
                "humidity String,"+
                "noise String," +
                "co2 String,"+
                "voc String," +
                "pm25 String,"+
                "formadehyde String"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }

}

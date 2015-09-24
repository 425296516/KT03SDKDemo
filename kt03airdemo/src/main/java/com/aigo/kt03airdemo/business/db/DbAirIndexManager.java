package com.aigo.kt03airdemo.business.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.goyourfly.ln.Ln;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangcirui on 15/9/3.
 */
public class DbAirIndexManager {



    private DbAirIndexInfoHelper mDbAirIndexInfoHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public DbAirIndexManager(Context context) {
        mDbAirIndexInfoHelper = new DbAirIndexInfoHelper(context);
    }


    public int insert(DbAirIndexObject dbAirIndexObject) {

        mSqLiteDatabase = mDbAirIndexInfoHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

       // contentValues.put("_id",dbAirIndexObject.getId());

        contentValues.put("pubtime", dbAirIndexObject.getPubtime());
        contentValues.put("temperature", dbAirIndexObject.getTemperature());
        contentValues.put("humidity", dbAirIndexObject.getHumidity());
        contentValues.put("noise", dbAirIndexObject.getNoise());
        contentValues.put("co2", dbAirIndexObject.getCo2());
        contentValues.put("voc", dbAirIndexObject.getVoc());
        contentValues.put("pm25", dbAirIndexObject.getPm25());
        contentValues.put("formadehyde", dbAirIndexObject.getFormadehyde());

        int result = (int) mSqLiteDatabase.insert(DbAirIndexInfoHelper.TABLE_AIR_INDEX, null, contentValues);
        mSqLiteDatabase.close();

        return result;
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
    public DbAirIndexObject queryById(int deviceId) {

        mSqLiteDatabase = mDbAirIndexInfoHelper.getReadableDatabase();
        String sql = "select * from " + mDbAirIndexInfoHelper.TABLE_AIR_INDEX + " where _id = " + deviceId;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        DbAirIndexObject dbAirIndexObject = new DbAirIndexObject();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
            String humidity = cursor.getString(cursor.getColumnIndex("humidity"));
            String noise = cursor.getString(cursor.getColumnIndex("noise"));
            String co2 = cursor.getString(cursor.getColumnIndex("co2"));
            String voc = cursor.getString(cursor.getColumnIndex("voc"));
            String pm25 = cursor.getString(cursor.getColumnIndex("pm25"));
            String formadehyde = cursor.getString(cursor.getColumnIndex("formadehyde"));
            String pubtime = cursor.getString(cursor.getColumnIndex("pubtime"));

            dbAirIndexObject.setId(id);
            dbAirIndexObject.setTemperature(temperature);
            dbAirIndexObject.setHumidity(humidity);
            dbAirIndexObject.setNoise(noise);
            dbAirIndexObject.setCo2(co2);
            dbAirIndexObject.setVoc(voc);
            dbAirIndexObject.setPm25(pm25);
            dbAirIndexObject.setFormadehyde(formadehyde);
            dbAirIndexObject.setPubtime(pubtime);

        }
        cursor.close();
        mSqLiteDatabase.close();
        return dbAirIndexObject;
    }

    public List<DbAirIndexObject> queryAll() {

        mSqLiteDatabase = mDbAirIndexInfoHelper.getReadableDatabase();
        List<DbAirIndexObject> list = new ArrayList<DbAirIndexObject>();
        String sql = "select * from " + mDbAirIndexInfoHelper.TABLE_AIR_INDEX;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String pubtime = cursor.getString(cursor.getColumnIndex("pubtime"));
            String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
            String humidity = cursor.getString(cursor.getColumnIndex("humidity"));
            String noise = cursor.getString(cursor.getColumnIndex("noise"));
            String co2 = cursor.getString(cursor.getColumnIndex("co2"));
            String voc = cursor.getString(cursor.getColumnIndex("voc"));
            String pm25 = cursor.getString(cursor.getColumnIndex("pm25"));
            String formadehyde = cursor.getString(cursor.getColumnIndex("formadehyde"));

            DbAirIndexObject dbAirIndexObject = new DbAirIndexObject();

            dbAirIndexObject.setId(id);
            dbAirIndexObject.setTemperature(temperature);
            dbAirIndexObject.setHumidity(humidity);
            dbAirIndexObject.setNoise(noise);
            dbAirIndexObject.setCo2(co2);
            dbAirIndexObject.setVoc(voc);
            dbAirIndexObject.setPm25(pm25);
            dbAirIndexObject.setFormadehyde(formadehyde);
            dbAirIndexObject.setPubtime(pubtime);

            list.add(dbAirIndexObject);
        }
        cursor.close();
        mSqLiteDatabase.close();
        return list;
    }

    public List<ArrayList<String>> queryAll2() {

        mSqLiteDatabase = mDbAirIndexInfoHelper.getReadableDatabase();
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        String sql = "select * from " + mDbAirIndexInfoHelper.TABLE_AIR_INDEX;
        Cursor cursor = mSqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String pubtime = cursor.getString(cursor.getColumnIndex("pubtime"));
            String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
            String humidity = cursor.getString(cursor.getColumnIndex("humidity"));
            String noise = cursor.getString(cursor.getColumnIndex("noise"));
            String co2 = cursor.getString(cursor.getColumnIndex("co2"));
            String voc = cursor.getString(cursor.getColumnIndex("voc"));
            String pm25 = cursor.getString(cursor.getColumnIndex("pm25"));
            String formadehyde = cursor.getString(cursor.getColumnIndex("formadehyde"));

            ArrayList beanList =  new ArrayList<String>();
            //DbAirIndexObject dbAirIndexObject = new DbAirIndexObject();

            //dbAirIndexObject.setId(id);
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            beanList.add(dataFormat.format(Long.parseLong(pubtime)*1000));
            beanList.add(formadehyde+"mg/m3");
            beanList.add(voc+"等级");
            beanList.add(pm25+"mg/m3");
            beanList.add(temperature+"摄氏度");
            beanList.add(humidity+"%");
            beanList.add(noise+"等级");
            beanList.add(co2+"ppm");

            list.add(beanList);
        }
        cursor.close();
        mSqLiteDatabase.close();
        return list;
    }


    public boolean deleteById(int id) {
        mSqLiteDatabase = mDbAirIndexInfoHelper.getWritableDatabase();
        String sql = "delete from " + mDbAirIndexInfoHelper.TABLE_AIR_INDEX +
                " where _id = " + id;
        mSqLiteDatabase.execSQL(sql);
        mSqLiteDatabase.close();
        return true;
    }

    public boolean deleteAll() {
        mSqLiteDatabase = mDbAirIndexInfoHelper.getWritableDatabase();
        String sql = "delete from " + mDbAirIndexInfoHelper.TABLE_AIR_INDEX ;
        mSqLiteDatabase.execSQL(sql);
        mSqLiteDatabase.close();
        return true;
    }


}

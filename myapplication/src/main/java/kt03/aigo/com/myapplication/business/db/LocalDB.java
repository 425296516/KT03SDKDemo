package kt03.aigo.com.myapplication.business.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import kt03.aigo.com.myapplication.business.bean.KeyColumn;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;

/**
 * @author work
 */
public class LocalDB extends SQLiteOpenHelper {

    final static String TAG = LocalDB.class.getSimpleName();

    private static String DB_NAME = "Local.db";
    private static String ASSETS_NAME = "Local.db";

    final static String INTERNAL_TAB = "i18n";
    final static String KEY_COLUMN = "key";

    private SQLiteDatabase mRmtDB;
    private Context myContext;

    // 用户数据库文件的版本
    private static final int DB_VERSION = 1;

    public LocalDB(Context context, String name, CursorFactory factory,
                   int version) {
        // 必须通过super调用父类当中的构造函数
        super(context, name, null, version);
        this.myContext = context;
    }

    public LocalDB(Context context, String name, int version) {
        this(context, name, null, version);
        this.myContext = context;
    }

    public LocalDB(Context context, String name) {
        this(context, name, DB_VERSION);
        this.myContext = context;
    }

    public LocalDB(Context context) {
        this(context, context.getDatabasePath(DB_NAME).toString());
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        File dbf = myContext.getDatabasePath(DB_NAME);
        if (dbf.exists() == false) {

            // if (Value.initial==true){
            Log.v(TAG, "path is " + myContext.getDatabasePath(DB_NAME));
            // 创建数据库
            try {
                File dir = myContext.getDatabasePath(DB_NAME);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // dbf = new File(DB_PATH + DB_NAME);
                if (dbf.exists()) {
                    dbf.delete();
                }
                // SQLiteDatabase.openOrCreateDatabase(dbf, null);
                // 复制asseets中的db文件到DB_PATH下
                copyDataBase();
                // copyBigDataBase();
            } catch (IOException e) {
                throw new Error("user database create failed");
            }
        }

    }


    public LocalDB open() {
        String myPath = myContext.getDatabasePath(DB_NAME).toString();

        mRmtDB = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

        return this;
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);

        String outFileName = myContext.getDatabasePath(DB_NAME).toString();
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    @Override
    public synchronized void close() {
        if (mRmtDB != null) {
            mRmtDB.close();
            // System.out.println("关闭成功1");
        }
        super.close();
        // System.out.println("关闭成功2");
    }

    /**
     * 该函数是在第一次创建的时候执行， 实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * 数据库表结构有变化时采用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @SuppressWarnings("unused")
    private SQLiteDatabase openOrCreateDatabase(String string, Object object) {

        return null;
    }


    public ArrayList<KeyColumn> getKeyColumn(int device) {
        ArrayList<KeyColumn> keyColumns = new ArrayList<KeyColumn>();
        // get all remote data
        ETLogger.debug("device=" + device);
        Cursor c = mRmtDB.query(KEY_COLUMN, null, " device =?",
                new String[]{String.valueOf(device)}, null, null, null);
        c.moveToFirst();

        do {
            int id = c.getInt(0);
            String name = c.getString(1);
            int column = c.getInt(3);
            int type = c.getInt(4);
//			Log.v(TAG, "KeyColumn"+ id +"  " +name+"  "+column+"  "+type );
            KeyColumn kc = new KeyColumn(id, name, device, type, column);
            Log.d(TAG,kc.toString());
            keyColumns.add(kc);
        } while (c.moveToNext());

        c.close();

        return keyColumns;

    }


    public String strTranslator(String name) {
//		String localeLanguage = Locale.getDefault().getLanguage();
        String traName = "";
        int column = 0;

        if (Globals.LocalLanguage == 0) {
            column = 4; // get brand chinese name
        } else if (Globals.LocalLanguage == 1) {
            column = 3; // get brand taiwan name
        } else {
            column = 2; // get brand english default name
        }

        Cursor c = mRmtDB.query(INTERNAL_TAB, null, "name =?  ",
                new String[]{name}, null, null, null);
        if (c.moveToFirst()) {

            byte[] val = c.getBlob(column);
            if (val != null && Globals.LocalLanguage == 0) {
                try {
                    traName = new String(val, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                traName = c.getString(column);
            }

        } else {
            traName = name;
        }
        c.close();

        return traName;
    }
}
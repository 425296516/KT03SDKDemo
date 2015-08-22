package kt03.aigo.com.myapplication.business.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;




import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import kt03.aigo.com.myapplication.business.bean.Remote;

/**
 * @author work
 * 
 */
public class UserDB extends SQLiteOpenHelper {

	final static String TAG = "UserDB";

	final static String REMOTE = "remote";
	public static final String REMOTE_ID = "_id";
	public static final String REMOTE_TYPE = "type";
	public static final String REMOTE_ROOM = "room";
	public static final String REMOTE_NAME = "name";
	public static final String REMOTE_BRAND = "brand";
	public static final String REMOTE_MODEL = "model";
	public static final String REMOTE_FILENAME = "filename";
	public static final String REMOTE_LEARN = "learn";
	public static final String REMOTE_REMOTE = "remote";
	private static String DB_NAME = "User.db";
	private static String ASSETS_NAME = "User.db";

	final static String TESTTAB = "test_tab";

	public static final String KEY_NAME = "key_name";
	public static final String KEY_DATA = "key_data";

	final static String LEARNTAB = "learn_tab";
	public static final String LEARN_DEVICE = "device";
	public static final String LEARN_NUMBER = "number";
	public static final String LEARN_INDEX = "type";
	public static final String LEARN_NAME = "name";
	public static final String LEARN_DATA = "data";

	private SQLiteDatabase myUserDB;
	private Context myContext;
	// 用户数据库文件的版本
	private static final int DB_VERSION = 1;
	// 数据库文件目标存放路径为系统默认位置，
	@SuppressLint("SdCardPath")
	// private static String DB_PATH =
	// "/data/data/com.irda.irremote/databases/";
	/*private static File DB_FILE = RemoteApplication.mAppContext
			.getDatabasePath(DB_NAME);
	private static String DATA_PATH = RemoteApplication.mAppContext
			.getFilesDir().getPath();*/

	// 如果你想把数据库文件存放在SD卡的话
	// private static String DB_PATH =
	// android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
	// + "/arthurcn/drivertest/packfiles/";

	public UserDB(Context context, String name, CursorFactory factory,
			int version) {
		// 必须通过super调用父类当中的构造函数
		super(context, name, null, version);
		this.myContext = context;
	}

	public UserDB(Context context, String name, int version) {
		this(context, name, null, version);
		this.myContext = context;
	}

	public UserDB(Context context, String name) {
		this(context, name, DB_VERSION);
		this.myContext = context;
	}

	public UserDB(Context context) {
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

	/*public void initialDataBase() throws IOException {
		// boolean dbExist = checkDataBase();
		File dbf = new File(DATA_PATH + myContext.getPackageName()
				+ "/databases/" + DB_NAME);

		try {
			File dir = new File(DATA_PATH + myContext.getPackageName()
					+ "/databases/");
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

	}*/

	public UserDB open() {
		String myPath = myContext.getDatabasePath(DB_NAME).toString();

		myUserDB = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

		return this;

	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
		// Path to the just created empty db
		// String outFileName = DATA_PATH + myContext.getPackageName() +
		// "/databases/" + DB_NAME;
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

	public void copyDataBase2SD() throws IOException {

		String filepath = Environment.getExternalStorageDirectory().getPath();
		filepath += File.separator + "ETEK";
		File file;
		File dir = new File(filepath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		file = new File(filepath, DB_NAME);
		if (file.exists()) {
			file.delete();
		}
		String inFileName = myContext.getDatabasePath(DB_NAME).toString();
		// Open your local db as the input stream
		InputStream myInput = new FileInputStream(inFileName);
		// Path to the just created empty db

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(file);
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
		if (myUserDB != null) {
			myUserDB.close();
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
		// TODO Auto-generated method stub
		return null;
	}

	public void saveRemote(Remote remote) {
		
		String jasonRemote = new Gson().toJson(remote);

		ContentValues values = new ContentValues();
		values.put(REMOTE_ID, remote.getId());
		values.put(REMOTE_TYPE, remote.getType());
		values.put(REMOTE_ROOM, remote.getRoom_no());
		values.put(REMOTE_NAME, remote.getName());
		values.put(REMOTE_BRAND, remote.getBrand_id());
		values.put(REMOTE_MODEL, remote.getModel());
//		values.put(REMOTE_FILENAME, remote.getFileName());
		values.put(REMOTE_LEARN, remote.getLearn());
		values.put(REMOTE_REMOTE, jasonRemote);
		myUserDB.insert(REMOTE, null, values);

//		Log.v(TAG, "insert remote 2 database");
	}

	public ArrayList<Remote> getAllRemotes() {
		ArrayList<Remote> remotes = new ArrayList<Remote>();
		// Log.v(TAG, "saveAllKeyTabValue start");
		Cursor c = myUserDB.query(REMOTE, null, null, null, null, null, null);
		remotes.clear();
		if (c.moveToFirst()) {
			do {
				Remote remote = new Remote();
				String remoteStr = c.getString(8);
				if (remoteStr != null && remoteStr.equals("") == false) {

                    remote =new Gson().fromJson(remoteStr,Remote.class);

					//remote = JSON.parseObject(remoteStr, Remote.class);
					// Log.v("test", jasonStr);
//					Logger.debug(remoteStr);
					// Log.v("test", remote.getDesc());

				}
				// remote.setId(c.getString(0));
				// remote.setType(c.getInt(1));
				// remote.setRoom_no(c.getInt(2));
				// remote.setName(c.getString(3));
				// remote.setBrand_id(c.getInt(4));
				// remote.setModel(c.getString(5));
				// remote.setFileName(c.getString(6));
				// remote.setLearn(c.getInt(7));

				remotes.add(remote);
				// Log.v(TAG, remote.getDesc());

			} while (c.moveToNext());

			c.close();
			return remotes;

		} else {
			return null;
		}
	}
}
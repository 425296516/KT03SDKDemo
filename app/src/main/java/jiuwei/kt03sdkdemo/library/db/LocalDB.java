package jiuwei.kt03sdkdemo.library.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import jiuwei.kt03sdkdemo.library.bean.KeyColumn;
import jiuwei.kt03sdkdemo.library.util.ETLogger;
import jiuwei.kt03sdkdemo.library.util.Globals;
import jiuwei.kt03sdkdemo.library.util.RemoteApplication;

/**
 * @author work
 * 
 */
public class LocalDB extends SQLiteOpenHelper {

	final static String TAG = "LocalRemoteDB";

	final static String REMOTE = "remote";
	public static final String REMOTE_ID = "_id";
	public static final String REMOTE_BRAND = "brand";
	public static final String REMOTE_PRODUCT = "product";
	public static final String REMOTE_INDEX = "index";
	public static final String REMOTE_TYPE = "type";

	private static String DB_NAME = "Local.db";
	private static String ASSETS_NAME = "Local.db";

	public static final String KEY_NAME = "key_name";
	public static final String KEY_DATA = "key_data";


	
	final static String INTERNAL_TAB= "i18n";
	public static final String BRAND = "name";
	public static final String BRAND_EN = "name_en";
	public static final String BRAND_ZH = "name_zh";
	public static final String BRAND_TW = "name_tw";
	public static final String BRAND_ID = "_id";
	public static final String Letter = "letter";
	public static final String PINYIN = "py";
	
	final static String KEY_COLUMN = "key";
	public static final String KEY_ID = "_id";

	
	private SQLiteDatabase mRmtDB;
	private Context myContext;
	
	
	// 用户数据库文件的版本
	private static final int DB_VERSION = 1;
	// 数据库文件目标存放路径为系统默认位置，
	@SuppressLint("SdCardPath")
	// private static String DB_PATH =
	// "/data/data/com.irda.irremote/databases/";
	private static File DB_FILE = RemoteApplication.mAppContext
			.getDatabasePath(DB_NAME);
	private static String DATA_PATH = RemoteApplication.mAppContext.getFilesDir()
			.getPath();

	// 如果你想把数据库文件存放在SD卡的话
	// private static String DB_PATH =
	// android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
	// + "/arthurcn/drivertest/packfiles/";

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

	public void initialDataBase() throws IOException {
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

	}

	public LocalDB open() {
		String myPath = myContext.getDatabasePath(DB_NAME).toString();

		mRmtDB = SQLiteDatabase.openDatabase(myPath, null,
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
		// TODO Auto-generated method stub
		return null;
	}

	/*public ArrayList<RemoteProduct> getRemoteProducts(int type) {
		ArrayList<RemoteProduct> rmtPdts = new ArrayList<RemoteProduct>();
		// Log.v(TAG, "saveAllKeyTabValue start");
		Cursor c = mRmtDB.query(REMOTE, null, null, null, null, null, null);
		rmtPdts.clear();
		if (c.moveToFirst()) {
			do {
				RemoteProduct rmtPdt = new RemoteProduct();
				rmtPdt.setBrand(c.getString(1));
				rmtPdt.setProduct(c.getString(2));
				rmtPdt.setIndex(Integer.valueOf(c.getString(3)));
				rmtPdt.setType(c.getString(4));

				rmtPdts.add(rmtPdt);

			} while (c.moveToNext());

			c.close();
			return rmtPdts;

		} else {
			return null;
		}

		// Log.v(TAG, "devicetype--->"+ Value.rmtDevs.toString());
	}
*/

	public ArrayList<String> getBrand(String _type) {
		ArrayList<String> brands = new ArrayList<String>();
		// get all remote data
		Cursor c = mRmtDB.query(REMOTE, null, " type =?",
				new String[] { _type }, null, null, null);
		c.moveToFirst();

		do {
			String menufactory = c.getString(1);

			if (compareListValue(brands, menufactory) == -1) {
				// Log.v(TAG, "menufactory = " + menufactory);
				brands.add(menufactory);

			}
		} while (c.moveToNext());

		c.close();

		return brands;

	}
	public ArrayList<KeyColumn> getKeyColumn(int device) {
		ArrayList<KeyColumn> keyColumns = new ArrayList<KeyColumn>();
		// get all remote data
		ETLogger.debug("device=" + device);
		Cursor c = mRmtDB.query(KEY_COLUMN, null, " device =?",
				new String[] { String.valueOf(device) }, null, null, null);
		c.moveToFirst();

		do {
			int id = c.getInt(0);
			String name = c.getString(1);
			int column = c.getInt(3);
			int type = c.getInt(4);
//			Log.v(TAG, "KeyColumn"+ id +"  " +name+"  "+column+"  "+type );
			KeyColumn kc = new KeyColumn(id,name,device,type,column);	
			keyColumns.add(kc);
		} while (c.moveToNext());

		c.close();

		return keyColumns;

	}
	
	
	public  ArrayList<KeyColumn> getKeyArray() {
		ArrayList<KeyColumn> keyColumns = new ArrayList<KeyColumn>();
		// get all remote data
		Cursor c = mRmtDB.query(KEY_COLUMN, null, " device =?",
				new String[] { String.valueOf(100) }, null, null, null);
		c.moveToFirst();

		do {
			int id = c.getInt(0);
			String name = c.getString(1);
			int column = c.getInt(3);
			int type = c.getInt(4);
//			Log.v(TAG, "KeyColumn"+ id + name+"  " +column+"  "+twName+"  "+chName +"  "+ enName);
			KeyColumn kc = new KeyColumn(id,name,100,type,column);	
			keyColumns.add(kc);
		} while (c.moveToNext());

		c.close();

		return keyColumns;

	}
	/**
	 * compareListValue compare arraylist all members
	 * 
	 * @param datas
	 * @param data
	 * @return if get same members return >0 or no same member return -1
	 */
	public int compareListValue(ArrayList<String> datas, String data) {
		int i;
		for (i = 0; i < datas.size(); i++) {
			if (datas.get(i).equals(data)) {
				return i;
			}
		}
		return -1;

	}

	public ArrayList<String> translateBrands(ArrayList<String> brands) {
		ArrayList<String> newBrands = new ArrayList<String>();
		String localeLanguage = Locale.getDefault().getLanguage();

		int column = 0;

		if (localeLanguage.equals("zh")) {
			column = 4; // get brand chinese name
		} else if (localeLanguage.equals("tw")) {
			column = 3; // get brand taiwan name
		} else {
			column = 2; // get brand english default name
		}
		// Log.v(TAG, "get language  " + localeLanguage + "    column  " +
		// column);
		for (String brand : brands) {

			Cursor c = mRmtDB.query(INTERNAL_TAB, null, "name =?  ",
					new String[] { brand }, null, null, null);
			if (c.moveToFirst()) {
				// Log.v(TAG, "cursor successed");
				byte[] val = c.getBlob(column);
				if (val != null && localeLanguage.equals("zh")) {
					try {
						newBrands.add(new String(val, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					newBrands.add(c.getString(column));
				}

			} else {
				newBrands.add(brand);
			}
			c.close();
		}

		return newBrands;

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
					new String[] { name }, null, null, null);
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
	
	
	public String deTranslateBrands(String brand) {
//		String localeLanguage = Locale.getDefault().getLanguage();
		String newBrand = "";
		String column = "name_en = ?";

		if (Globals.LocalLanguage == 0) {
			 column = "name_zh = ?";
			 brand = brand.trim();  
//			 brand = brand.substring(0,brand.length()-1);
		} else if (Globals.LocalLanguage == 1) {
			 column = "name_tw = ?";
		} else {
			 column = "name_en = ?";
		}
	
	
			Cursor c = mRmtDB.query(INTERNAL_TAB, null, column,
					new String[] { brand }, null, null, null);
			if (c.moveToFirst()) {
			
				newBrand = c.getString(1);
				

			} 
			c.close();
		

		return newBrand;

	}

	public ArrayList<String> getRemoteIndexs(String type,String brand) {
		ArrayList<String> remotes = new ArrayList<String>();
		// get all remote data
		Cursor c = mRmtDB.query(REMOTE, null, " type = ? and brand = ? ",
				new String[] {type,brand}, null, null, null);
		c.moveToFirst();

		do {
			String index = c.getString(3);

			if (compareListValue(remotes, index) == -1) {
				// Log.v(TAG, "menufactory = " + menufactory);
				remotes.add(index);

			}
		} while (c.moveToNext());

		c.close();

		return remotes;

	}

	

}
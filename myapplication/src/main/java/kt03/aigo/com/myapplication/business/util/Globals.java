package kt03.aigo.com.myapplication.business.util;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.ModelNum;
import kt03.aigo.com.myapplication.business.bean.Remote;


public class Globals {
	public static int BUTTON_WIDTH = 70;
	public static float DPI = 1.0F;
	// public static CommandManager commandManager;
	public final static int ID_TERMINAL = 1;
	public final static int ID_INFORLOCATION = 2;
	public final static int ID_LEARN_EDIT = 3;
	public final static int ID_ADDREMOTE = 4;
	public final static int WHAT_LEARN = 1;
	public static int DEVICE = DeviceType.DUMMY;
	// public static final String REMOTES = null;
	public static String ADD_REMOTE;
	public static boolean isAdd;
	// public static final HashMap<String, Integer> BG_COLORS;
	public static String BRANDS;
	public static String CACHE_FOLDER;
	public static String CODES;
	// public static String DEVICEs;
	// public static String TERMINAL_TYPE;

	public static Boolean VIBRATION;
	public static Boolean POWER;
	public static String TEST_MODE;
	public static String NEW_REMOTE;
	public static String REMOTE_SHARED_PREFF;

	public static boolean ISLEARN;
	public static boolean INITIAL;
	public static boolean NETCONNECT;
	public static ArrayList<Brand> MBrands;
	public static ArrayList<Remote> mRemotes = new ArrayList<Remote>();
	public static int mIndex = 0;
	public static Brand MBrand;
	// public static int TYPE = ApplianceType.TV;
	public final static int LEARN_RESUIT_REQUEST = 100;

	public static String GETSERVERBRAND = "http://222.191.229.234:10068/SmartHomeServer/wyf/getBrand/";
	public static String GETSERVERKEYWOARDS = "http://222.191.229.234:10068/SmartHomeServer/wyf/getModelNumberByKeywords/";
	public static String GETSERVERREMOTEKEY = "http://222.191.229.234:10068/SmartHomeServer/wyf/getRemoteKey/";
	public static String GETSERVERSEARCHREMOTE = "http://222.191.229.234:10068/SmartHomeServer/wyf/getSearchRemoteKey/";
	

//	static {
//
//		CACHE_FOLDER = "cache";
//		REMOTE_SHARED_PREFF = "SHARED_REMOTE";
//		VIBRATION = false;
//		ISLEARN = false;
//		POWER = false;
//		NEW_REMOTE = "new_remote";
//
//	}

	public static int toPx(float paramFloat) {
		return (int) (paramFloat * DPI);
	}

	public static int HEADER = 0x99;

	public static int flag = 0;

	public static String[] REMOTE_TYPE;
	public static int selectStatus = 0;

	public static Boolean audio = true;
	// public static Boolean powerSupply= false;
	public static Boolean modeLearning = false;
	public static String currentKey = null;

	public static Boolean exist = false;

	// public static int cKey = 0;

	public static int screenWidth;
	public static int screenHeight;

	// public static HashMap<String, String> keyRemoteTab ;
	// public static ArrayList<KeyValue> keyValueTab ;
	public static int test_mode = 0;
	public static int totalRemote = 0;

	// public static HashMap<String,String > KeyTable = new HashMap<String,
	// String>();

	public static boolean vibrate = false;
	public static boolean animation = false;
	public static boolean proOrCh = false;

	public static String tvLocation;
	public static String tvProvider;

	public static final int APP_TYPE = 6;
	public static final String CONF_NAME = "version.conf";
	public static final boolean CONTAIN_LOCAL_DATA = true;

	public static int MAX_DEV_ID = 6;

	public static final boolean STUDY_FUNC = true;
	public static IRKey KeyValue;

	// public static final String SYS_DB_NAME = "system.db";
	// public static final String SYS_DB_VER = "1.0";
	//
	// public static final String USER_DB_NAME = "user.db";
	// public static final String USER_DB_VER = "1.3";
	// public static final int WEIBO_CONTENT_COUNT = 10;

	public static boolean isDebug;
	public static boolean isFullDev;
	public static boolean isMultiThreadSend;
	public static int LocalLanguage;
	public static Remote mRemote;
	public static int deviceID = ApplianceType.TV;

	public static List<ModelNum> modelSearchs;;



	public final static int TypeArray[] = { ApplianceType.TV,
			ApplianceType.DVD, ApplianceType.STB, ApplianceType.FAN,
			ApplianceType.PROJECTOR, ApplianceType.AIR_CONDITIONER,
			ApplianceType.LIGHT, ApplianceType.CAMERA, ApplianceType.AMPLIFIER,
			ApplianceType.GAME, ApplianceType.OTHER };

	public static String getTypeStr(int type) {
		String str = "tv";
		switch (type) {
		case ApplianceType.TV: // tv
			str = "TV";
			break;
		case ApplianceType.DVD: // dvd
			str = "DVD";
			break;
		case ApplianceType.STB: // stb
			str = "STB";
			break;
		case ApplianceType.PROJECTOR: // pjt
			str = "PJT";
			break;
		case ApplianceType.FAN: // fan
			str = "FAN";
			break;
		case ApplianceType.AIR_CONDITIONER: // air
			str = "AIR";
			break;
		case ApplianceType.LIGHT: // light
			str = "LIGHT";
			break;
		case ApplianceType.CAMERA: // camera
			str = "CAM";
			break;
		case ApplianceType.AMPLIFIER: // amplify
			str = "AMP";
			break;
		case ApplianceType.GAME: // game
			str = "BOX";
			break;
		case ApplianceType.OTHER: // switch
			str = "SLR";
			break;
		case ApplianceType.IPTV: // iptv
			str = "IPT";
			break;

		default:
			str = "TV";
			break;
		}

		// TODO Auto-generated method stub
		return str;
	}

	public static int getDBType(int type) {
		int dbType = 0;
		switch (type) {
		case ApplianceType.TV: // tv
			dbType = 0;
			break;
		case ApplianceType.DVD: // dvd
			dbType = 1;
			break;
		case ApplianceType.STB: // stb
			dbType = 2;
			break;
		case ApplianceType.PROJECTOR: // pjt
			dbType = 4;
			break;
		case ApplianceType.FAN: // fan
			dbType = 3;
			break;
		case ApplianceType.AIR_CONDITIONER: // air
			dbType = 5;
			break;

		default:
			dbType = 0;
			break;
		}

		// TODO Auto-generated method stub
		return dbType;
	}


}

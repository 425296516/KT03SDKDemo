package kt03.aigo.com.myapplication.business.util;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.ModelNum;
import kt03.aigo.com.myapplication.business.bean.Remote;


public class Globals {
    public final static int DUMMY = 0X00;
	public static int DEVICE = DUMMY;


	public static boolean NETCONNECT;
	public static ArrayList<Brand> MBrands;
	public static ArrayList<Remote> mRemotes = new ArrayList<Remote>();
	public static Brand MBrand;

	public static String GETSERVERBRAND = "http://222.191.229.234:10068/SmartHomeServer/wyf/getBrand/";
	public static String GETSERVERKEYWOARDS = "http://222.191.229.234:10068/SmartHomeServer/wyf/getModelNumberByKeywords/";
	public static String GETSERVERREMOTEKEY = "http://222.191.229.234:10068/SmartHomeServer/wyf/getRemoteKey/";
	public static String GETSERVERSEARCHREMOTE = "http://222.191.229.234:10068/SmartHomeServer/wyf/getSearchRemoteKey/";

	public static Boolean exist = false;

	public static int LocalLanguage;
	public static Remote mRemote;

    //电器类型 : 空调
    public final static int AIR_CONDITIONER = 5;
	public static int deviceID = AIR_CONDITIONER;

	public static List<ModelNum> modelSearchs;;

	public static String getTypeStr(int type) {
		String str = "AIR";
		switch (type) {

		case AIR_CONDITIONER: // air
			str = "AIR";
			break;

		default:
			str = "AIR";
			break;
		}
		return str;
	}


    public static int getImgID(int type) {
        int bmp = 0;
        switch (type) {

            case Globals.AIR_CONDITIONER: // air conditioner
                bmp = R.drawable.ic_launcher;
                break;
            default:
                bmp = R.drawable.ic_launcher;
                break;
        }

        return bmp;
    }



}

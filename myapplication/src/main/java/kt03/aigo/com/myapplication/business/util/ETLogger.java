package kt03.aigo.com.myapplication.business.util;

/**
 * Created by zhangcirui on 15/8/18.
 */
import android.util.Log;

public class ETLogger {
    private static boolean info = false;
    private static boolean verse = false;
    private static boolean error = false;
    private static boolean debug = false;
    private static boolean waring = false;


    public static void init() {
        info = true;
        verse = true;
        error = true;
        debug = true;
        waring = true;

    }

    public static void info(String tag, String loggerStr) {
        if (info)
            Log.i(tag, loggerStr);
    }

    public static void verse(String tag, String loggerStr) {
        if (verse)
            Log.v(tag, loggerStr);
    }

    public static void error(String tag, String loggerStr) {
        if (error)
            Log.e(tag, loggerStr);
    }
    public static void error( String loggerStr) {
        if (error)
            Log.e("ERROR", loggerStr);
    }

    public static void debug(String tag, String loggerStr) {
        if (debug)
            Log.d(tag, loggerStr);
    }

    public static void waring(String tag, String loggerStr) {
        if (waring)
            Log.w(tag, loggerStr);
    }

    public static void debug(String loggerStr) {
        if (debug)
            Log.d("DUB", loggerStr);
    }
}

package kt03.aigo.com.myapplication.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.etek.ircore.RemoteCore;

import org.apache.http.util.EncodingUtils;


public final class Tools {

    private static Context mContext = RemoteApplication.mAppContext;

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }



    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();

        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }



    public static boolean testConnectivityManager() {
        ConnectivityManager conMan = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return true;
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return true;

        return false;

    }

    public static String readSysFile(String fileName) {
        int len;
        File readFile = new File(fileName);
        char[] buf = new char[5124];
        FileReader rd;
        char[] finBuf;
        try {
            rd = new FileReader(readFile);
            len = rd.read(buf);
            finBuf = new char[len];
            System.arraycopy(buf, 0, finBuf, 0, len);
            rd.close();
            return String.valueOf(finBuf);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static byte[] strToIntarray2(String str) {
        str = str.replace("0x", "");
        str = str.replace(",", "");

        str = str.substring(0, 256);

        byte[] data = hexStringToBytes(str);

        return data;
    }
}

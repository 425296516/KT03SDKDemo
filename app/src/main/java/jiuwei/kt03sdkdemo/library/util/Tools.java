package jiuwei.kt03sdkdemo.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;


import com.etek.ircore.RemoteCore;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public final class Tools {

    private static Context mContext = RemoteApplication.mAppContext;

    public static String shortToHex(short src) {
        StringBuilder stringBuilder = new StringBuilder("");

        int v = src & 0xffFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 4) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static String shortToHexString(short[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xffFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 4) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] shortArrayToByteArray(short[] s) {
        byte[] byteBuf = new byte[s.length];

        int j = 0;
        for (int i = 0; i < s.length / 2; i++) {

            byteBuf[j++] = (byte) ((s[i] >> 8) & 0xff);
            byteBuf[j++] = (byte) ((s[i]) & 0xff);
        }
        return byteBuf;
    }

    public static short[] byteArrayToShortArray(byte[] b) {
        short[] shortBuf = new short[b.length / 2];

        int j = 0;
        int i = 0;
        while (i < b.length) {

            shortBuf[j++] = (short) (((b[i++] & 0xFF) << 8) + (b[i++] & 0xFF));
        }
        return shortBuf;
    }

    public static byte[] readFileFromSDcard(String filename) throws Exception {

        File file = new File(Environment.getExternalStorageDirectory()
                // .getPath()
                // + File.separatorChar + AUDIO_RECORDER_FOLDER
                // + File.separatorChar +filename);
                , filename);
        FileInputStream in = new FileInputStream(file);

        byte[] buffer = new byte[in.available()];

        in.read(buffer);

        in.close();
        return buffer;
    }

    public static void saveFileToSDcard(String datas, String fileName) {

        File file = new File(Environment.getExternalStorageDirectory()
                // .getPath()
                // + File.separatorChar + AUDIO_RECORDER_FOLDER
                // + File.separatorChar +filename);
                , fileName);
        try {

            FileOutputStream os = new FileOutputStream(file);// 输出文件流
            os.write(datas.getBytes());
            os.flush();
            os.close();
            Log.d("CodeWatch", "save successed");
            // Toast.makeText(mContext, fileName+"save successed",
            // Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] readFile(String fileName) throws IOException {
        byte[] buffer = null;
        try {
            FileInputStream fin = mContext.openFileInput(fileName);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);

            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;

    }

    public static String readRemote(File fileName) throws IOException {
        byte[] buffer = null;
        String res = null;
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

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

    public static String bytesToHexString(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
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
            // Log.v("remoteSend", "data ----> " + d[i]);
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static int getValidLearnData(byte datas[]) {
        int i, length = 0, index;
        index = datas[2] + 2;
        for (i = index; i < 64; i++) {
            byte temp = datas[i];
            if (((temp & 0x0f) == 0x00) || ((temp & 0xf0) == 0x00)) {
                length = i + 2;
                return length;
            }
            // Log.v(TAG, "learnData[" + i +"]---------->"+ datas[i]);
        }
        // Log.v(TAG, "learn data length --->" + length);
        return 64;
    }

    public static String bytesToHex(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");

        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static boolean testConnectivityManager() {
        ConnectivityManager conMan = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // mobile 3G Network
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        // Log.d(mobile.toString());
        // wifi Network
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        // Log.d(wifi.toString());
        // local Network

        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
            return true;
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
            return true;

        return false;

    }

    public static void saveDocumnet(String temp, String fileName) {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        filepath += File.separator + "ETEKRemote";
        File file;
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(filepath, fileName);
        if (file.exists()) {
            file.delete();
        }

        try {

            FileOutputStream os = new FileOutputStream(file);// 输出文件流
            os.write(temp.getBytes());
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDocumnet(String temp, String fileName,
                                    String filePath) {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        filepath += File.separator + filePath;
        File file;
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(filepath, fileName);
        if (file.exists()) {
            file.delete();
        }

        // TODO Auto-generated method stub
        try {

            FileOutputStream os = new FileOutputStream(file);// 输出文件流
            os.write(temp.getBytes());
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String ET_IR_SEND = "/sys/class/etek/sec_ir/ir_send";

    public static int getDeviceType() {
        File file = new File(ET_IR_SEND);
        FileReader rd;

        char[] buf = new char[20];
        try {
            rd = new FileReader(file);
            int len = rd.read(buf);
            // learnBuf = new char[len];
            // System.arraycopy(buf, 0, learnBuf, 0, len);
            rd.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

        if (String.valueOf(buf).equalsIgnoreCase("0X0f,0X01,0X15,0X01,")) {
            RemoteCore.IRinit();
            return DeviceType.ET4007;

        } else if (String.valueOf(buf).equalsIgnoreCase("0X0e,0X03,0X03,0X01,")) {
            RemoteCore.IRinit();
            return DeviceType.ET4003;

        } else {
            return DeviceType.DUMMY;

        }

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

    public static char[] readSysFileChar(String fileName) {
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
            return finBuf;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

    }

    public static Boolean writeSysFile(String fileName, String content) {

        File writeFile = new File(fileName);
        FileWriter fr = null;

        try {
            fr = new FileWriter(writeFile);
            fr.write(content);
            fr.close();
            return true;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }

    }

    public static byte[] strToIntarray(String str) {
        String[] codeStrs = str.split(",");
        int len = codeStrs.length;
        byte[] datas = new byte[len];
        for (int i = 0; i < len; i++) {
            int tmp = Integer.valueOf(codeStrs[i]);
            datas[i] = (byte) tmp;
        }
        return datas;

    }

    public static byte[] strToIntarray2(String str) {
        str = str.replace("0x", "");
        str = str.replace(",", "");

        str = str.substring(0, 256);

        byte[] data = hexStringToBytes(str);

        return data;

    }
}

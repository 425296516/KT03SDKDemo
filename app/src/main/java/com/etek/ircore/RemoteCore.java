package com.etek.ircore;


import android.util.Log;

import jiuwei.kt03sdkdemo.library.bean.IRCode;
import jiuwei.kt03sdkdemo.library.util.Tools;


public class RemoteCore {
    final static String _CONTROL_SEND_CODE_1_ = "53";
    final static int _CONTROL_SEND_CODE = 0x53;

    final static String TAG = "RemoteComm";
    private static final String libSoName = "IRCore";
    static {
        System.loadLibrary(libSoName);
    }

    public static void SendRemote(String remoteData) {

        if (remoteData == null || remoteData.equalsIgnoreCase("")) {
            return;
        }
        Log.v(TAG, remoteData);

        byte[] sendData = Tools.hexStringToBytes(remoteData);
        int length = sendData.length;

        sendIRCode(sendData, length);
    }


    // public static String getLearnData (String data){
    // String keyDataStr;
    // keyDataStr = _ET4003_CONTROL_SEND_CODE_2_ +data;
    // return keyDataStr;
    // }

    public static String readLearnData() {

        byte[] learnData = readLearnIRCode();
        String lrnDtStr = Tools.bytesToHexString(learnData);
        // Log.v(TAG, "learn data --->" + lrnDtStr);
        return lrnDtStr;

    }

    public static int learnRemoteLoop(int timeout) {

        learnIRCodeStart();
        // String lrnDtStr;
        setLearnTimeout(timeout);

        // while(true){
        int status = learnIRCodeMain();
        if (status == -1) {
            Log.v(TAG, "learn remote  device error ");
            // lrnDtStr="device error";
            // remoteLearnStop();
            return status;
        } else if (status == 0) {
            Log.v(TAG, "learn remote  device timeout ");
            // lrnDtStr="time out";
            // remoteLearnStop();
            return status;
        } else {
            // byte[] learnData= readLearnIRCode();
            // lrnDtStr = Tools.bytesToHexString(learnData);
            // Log.v(TAG, "learn remote data  --->" + lrnDtStr);
            return status;
        }

    }



    public static String encodeRemoteData(String data, String type) {
        if ("".equals(data)) {
            return null;
        }
        String temp;
        byte[] data1 = Tools.hexStringToBytes(data);

        byte[] encodeData = Encode(data1, type);
        temp = Tools.bytesToHexString(encodeData);
        temp = _CONTROL_SEND_CODE_1_ + temp;
        // Log.v(TAG, "encode data ->" + temp);
        return temp;
    }

    public native static void sendIRCode(byte[] data, int length);

    public native static void learnIRCodeStart();

    public native static void learnIRCodeStop();

    public native static byte[] readLearnIRCode();

    public native static void sendLearnCode(byte[] data);

    public native static int IRinit();

    public native static void Finish();

    public native static void setLearnTimeout(int time);

    public native static int learnIRCodeMain();

    public native static byte[] Encode(byte[] data1, String data2);

    public native static byte[] getAirData(int[] data1);

    public native static byte[] getProntoAirData(int[] data1);

    public native static int sendIRRepeat();

    public native static byte[] prontoToETcode(int freq, int[] data);

    public native static IRCode prontoencode(byte[] data, String type);

    public native static IRCode ETcodeToPronto(byte[] data);

    public native static String[] getDataBase(int type, int index);

    public native static IRCode ET4007Learn(byte[] codes);

    public native static IRCode ET4003Learn(byte[] codes);
}
package com.aigo.kt03airdemo.business;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.aigo.kt03airdemo.business.db.DbAirIndexManager;
import com.aigo.kt03airdemo.business.db.DbAirIndexObject;
import com.aigo.kt03airdemo.business.task.TimerAirIndexTask;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.business.util.Constant;
import com.aigo.kt03airdemo.ui.excel.ExcelUtils;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by zhangcirui on 15/9/3.
 */
public class KT03AirModule {

    private static final String TAG = KT03AirModule.class.getSimpleName();
    private static KT03AirModule module;
    private OnPostListener mListener;
    private Context mContext;
    private Timer timer;
    //private AirIndex mAirIndex = null;

    public interface OnPostListener<T> {
        public void onSuccess(T result);

        public void onFailure(String err);
    }

    public void init(Context context) {
        mContext = context;
    }

    public static KT03AirModule getInstance() {
        if (module == null) {
            module = new KT03AirModule();
        }
        return module;
    }

    public void setDataChangedListener(OnPostListener listener) {
        mListener = listener;
    }

    public void getKT03AirInfo(final OnPostListener listener) {

        isFirst = true;
        getNetAirIndex(listener);

    }

    private long time = 0;

    private boolean isFirst = false;

    public void getNetAirIndex(final OnPostListener listener) {

        KT03AirModule.getInstance().getAirIndex(new OnPostListener<AirIndex>() {
            @Override
            public void onSuccess(AirIndex airIndex) {
                long currentTime = System.currentTimeMillis();
                if(isFirst){
                    listener.onSuccess(airIndex);
                    isFirst = false;
                }

               /* if(currentTime - time>1800000){
                    time = currentTime;*/
                DbAirIndexObject dbAirIndexObject = KT03Adapter.getInstance().getDbAirIndexObject(airIndex);
                DbAirIndexManager manager = new DbAirIndexManager(mContext);
                manager.insert(dbAirIndexObject);
                initData();
                //}
            }

            @Override
            public void onFailure(String err) {

            }
        });
    }
    private String[] title = { "时间", "甲醛", "VOC", "PM2.5", "温度", "湿度", "噪音", "CO2" };
    private File file;
    public void initData() {
        if(file ==null)
        file = new File(getSDPath() + "/KT03");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/air.xls", title);
        ExcelUtils.writeObjListToExcel(new DbAirIndexManager(mContext).queryAll2(), getSDPath() + "/KT03/air.xls", mContext);
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }


    public List<AirIndex> getHistoryAirIndex() {

        List<DbAirIndexObject> list = new DbAirIndexManager(mContext).queryAll();
        List<AirIndex> airIndexList = new ArrayList<AirIndex>();

        for (DbAirIndexObject dbAirIndexObject : list) {
            AirIndex airIndex = KT03Adapter.getInstance().getAirIndex(dbAirIndexObject);
            airIndexList.add(airIndex);
        }

        return airIndexList;
    }

    public List<AirIndex> getHistoryAirIndex2() {

        List<ArrayList<String>> list = new DbAirIndexManager(mContext).queryAll2();
        List<AirIndex> airIndexList = new ArrayList<AirIndex>();

        for (ArrayList<String> ls: list) {
            AirIndex airIndex = new AirIndex();
            airIndex.setTime(Long.parseLong(ls.get(0)));
            airIndex.setTemperature(ls.get(1));
            airIndex.setHumidity(ls.get(2));
            airIndex.setNoise(ls.get(3));
            airIndex.setCo2(ls.get(4));
            airIndex.setVoc(ls.get(5));
            airIndex.setPm25(ls.get(6));
            airIndex.setFormadehyde(ls.get(7));

            //AirIndex airIndex = KT03Adapter.getInstance().getAirIndex(dbAirIndexObject);
            airIndexList.add(airIndex);
        }

        return airIndexList;
    }


    public void getAirIndex(final OnPostListener listener) {
        timer = new Timer();
        TimerAirIndexTask timerTask = new TimerAirIndexTask(listener);

        timer.schedule(timerTask, 0, 600000);
    }

    /**
     * kt03的默认ip地址为-.-.-.1
     *
     * @param mContext
     * @return
     */
    public String ipIsWifi(Context mContext) {
        WifiManager wifimanage = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        //检查wifi是否开启

        if (!wifimanage.isWifiEnabled()) {
            wifimanage.setWifiEnabled(true);
        }

        WifiInfo wifiinfo = wifimanage.getConnectionInfo();
        Log.d(TAG, "ip=" +  intToIp(wifiinfo.getIpAddress()) + "ssid=" + wifiinfo.getSSID() + "mac=" + wifiinfo.getMacAddress());
        if(wifiinfo.getSSID().contains("KT03")){
            String ip = intToIp2(wifiinfo.getIpAddress());
            Log.d(TAG, "ip=" + ip + "ssid=" + wifiinfo.getSSID() + "mac=" + wifiinfo.getMacAddress());

            return ip + ".1";
        }else{
            return "192.168.4.1";
        }

    }

    private DatagramSocket dsres;
    private DatagramPacket dpres;

    private int mLocalPort;

    public void udpBroadcast() {

        new Thread() {
            @Override
            public void run() {

                Log.d(TAG, "udpBroadcast");
                if (dsres != null) {
                    dsres.close();
                    dsres = null;
                    dpres = null;
                }

                String message = "Are You Espressif IOT Smart Device?";
                // 广播的实现 :由客户端发出广播，服务器端接收
                String host = "255.255.255.255";//广播地址
                int sendPort = 1025;//广播的目的端口

                //String message = mSendMessage.getText().toString().trim();//用于发送的字符串
                try {
                    InetAddress adds = InetAddress.getByName(host);
                    DatagramSocket ds = new DatagramSocket();
                    DatagramPacket dp = new DatagramPacket(message.getBytes(),
                            message.length(), adds, sendPort);
                    ds.send(dp);
                    mLocalPort = ds.getLocalPort();

                    Log.d(TAG, "发送的广播消息：" + "local_ip=" + dp.getAddress().getHostAddress() + " local_port=" + mLocalPort);
                    ds.close();

                    String address = null;
                    int port = 0;
                    int localPort = 0;

                    byte[] buf = new byte[1024];//存储发来的消息
                    StringBuffer sbuf = new StringBuffer();
                    //绑定端口的
                    dsres = new DatagramSocket(mLocalPort);
                    dpres = new DatagramPacket(buf, buf.length);

                    Log.d(TAG, "监听广播端口打开：");
                    dsres.receive(dpres);
                    if (dpres != null && dsres != null) {
                        address = dpres.getAddress().getHostAddress();
                        localPort = dsres.getLocalPort();
                        port = dpres.getPort();
                        dsres.close();
                    }
                    int i;
                    for (i = 0; i < 1024; i++) {
                        if (buf[i] == 0) {
                            break;
                        }
                        sbuf.append((char) buf[i]);
                    }
                    Log.d(TAG, "收到广播消息：" + sbuf.toString() + "address=" + address + " port=" + port + "localport=" + localPort);

                    if (address != null) {
                        //SPManager.getInstance().setKT03IP("http://" + address);
                        Constant.URL_KT03 = "http://" + address;
                      /*  mKT03InfoObject.setIp(Constant.URL_KT03);
                        if (onGetKT03InfoListener != null) {
                            onGetKT03InfoListener.onGetKT03Info(mKT03InfoObject);
                            //onGetKT03InfoListener = null;
                        }
                        if (mTimer != null) {
                            mTimer.cancel();
                        }*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }

    //ip地址的前三位
    private static String intToIp2(long i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF);
    }
}

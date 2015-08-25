package kt03.aigo.com.myapplication.ui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kt03.aigo.com.myapplication.R;


public class DiscoverKT03Activity extends Activity {

    private static final String TAG = DiscoverKT03Activity.class.getSimpleName();
    private Button mSend;
    private EditText mIPInfo;
    private Handler mHandler;
    private EditText mSendMessage;
    private Button mReceiverMessage;
    private Timer timer;
    private int time = 0;
    private TextView mTextReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_discover_kt03);

        mSend = (Button) findViewById(R.id.send);
        mSendMessage = (EditText) findViewById(R.id.et_send_message);
       // mReceiverMessage = (Button) findViewById(R.id.btn_receiver_message);
        mTextReceiver = (TextView)findViewById(R.id.tv_receiver);
        mIPInfo = (EditText) findViewById(R.id.et_ip_info);

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    String message = (String) msg.obj;
                    String[] str = message.split("::");
                    String meg = str[0];
                    String ip =  str[1];

                    mTextReceiver.setText(meg+"");
                    mIPInfo.setText(ip + "");
                }else if(msg.what == 2) {
                    mTextReceiver.setText("");
                    mIPInfo.setText("");
                }

            }
        };

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread() {

                    @Override
                    public void run() {
                        Looper.prepare();

                        if(dsres != null){
                            dsres.close();
                            dsres = null;
                            dpres = null;

                        }

                        Message message = Message.obtain();
                        message.what = 2;
                        mHandler.sendMessage(message);

                        sendMsg(mSendMessage.getText().toString().trim());
                        receiveMsg();
                        Looper.loop();

                    }
                }.start();
            }
        });

       /* mReceiverMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        receiveMsg();
                        Looper.loop();
                    }
                }.start();

            }
        });*/
    }

    private int mLocalPort;

    public void sendMsg(String message) {

        // 广播的实现 :由客户端发出广播，服务器端接收
        String host = "255.255.255.255";//广播地址
        int port = 1025;//广播的目的端口
        //String message = mSendMessage.getText().toString().trim();//用于发送的字符串
        try {
            InetAddress adds = InetAddress.getByName(host);
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(message.getBytes(),
                    message.length(), adds, port);
            ds.send(dp);
            mLocalPort = ds.getLocalPort();

            Log.d(TAG, "发送的广播消息：" +"local_ip="+ dp.getAddress().getHostAddress()+" local_port="+mLocalPort);
            ds.close();


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private  DatagramSocket dsres;
    private  DatagramPacket dpres;

    public void receiveMsg() {

        String address = null;
        int port = 0;
        int localPort = 0;
        try {
            byte[] buf = new byte[1024];//存储发来的消息
            StringBuffer sbuf = new StringBuffer();
            //绑定端口的
             dsres = new DatagramSocket(mLocalPort);
             dpres = new DatagramPacket(buf, buf.length);

            Log.d(TAG, "监听广播端口打开：");
            dsres.receive(dpres);
            if(dpres!=null && dsres!=null) {

                 address = dpres.getAddress().getHostAddress();
                  localPort =  dsres.getLocalPort();

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
            Log.d(TAG, "收到广播消息：" + sbuf.toString()+"address="+address+" port="+port+"localport="+localPort);

            if(address!=null) {
                Message message = new Message();
                message.what = 1;
                //message.obj = sbuf.toString();
                message.obj = sbuf.toString() + "::" + address;
                mHandler.sendMessage(message);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
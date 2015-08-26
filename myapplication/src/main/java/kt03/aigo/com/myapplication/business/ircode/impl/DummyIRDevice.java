package kt03.aigo.com.myapplication.business.ircode.impl;

/**
 * Created by zhangcirui on 15/8/18.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.etek.ircore.RemoteCore;

import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.ircode.IInfraredDevice;


public class DummyIRDevice implements IInfraredDevice {
    private static final String TAG = DummyIRDevice.class.getSimpleName();

    private Context mContext;

    public DummyIRDevice() {
    }

    public DummyIRDevice(Context context) {
        this.mContext = context;
    }

    @SuppressLint("NewApi")
    @Override
    public void sendIr(int freq, int[] data) {

        Log.d(TAG, "sendIr..........####......freq = " + freq + " , data.length = " + (data == null ? -1 : data.length));

    }

    @Override
    public boolean hasLearn() {

        return false;
    }

    @Override
    public IRCode irCodeReceiver() {

        return null;
    }

    @Override
    public boolean sendLearnCmd(int cmd) {
        if (cmd == 1) {
            RemoteCore.learnIRCodeStart();
        } else {
            RemoteCore.learnIRCodeStop();
        }
        return false;
    }

    @Override
    public boolean getState() {

        return false;
    }
}
package kt03.aigo.com.myapplication.business.ircode.impl;

/**
 * Created by zhangcirui on 15/8/18.
 */
import android.annotation.SuppressLint;



import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.etek.ircore.RemoteCore;

import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.ircode.IInfraredDevice;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Tools;


public class DummyIRDevice implements IInfraredDevice
{
    private static final String TAG = DummyIRDevice.class.getSimpleName();
    private static final String ET_IR_SEND = "/sys/class/etek/sec_ir/ir_send";
    private static final String ET_IR_LEARN = "/sys/class/etek/sec_ir/ir_learn";
    private static final String ET_IR_STATE = "/sys/class/etek/sec_ir/ir_state";

    private Context mContext;

    public DummyIRDevice(){}

    public DummyIRDevice(Context context){
        this.mContext = context;
    }

    @SuppressLint("NewApi") @Override
    public void sendIr(int freq, int[] data)
    {
        // TODO Auto-generated method stub
        Log.d(TAG,"sendIr..........####......freq = " + freq + " , data.length = " + (data == null ? -1 : data.length));

        byte[] codes = RemoteCore.prontoToETcode(freq,
                data);

        Intent intent = new Intent ("BROADCAST_SEND");
        intent.putExtra("DATA", codes);
        mContext.sendBroadcast(intent);
        Log.d(TAG, "mcontext="+mContext+"codes="+codes.length);


//		String codeStr = Tools.bytesToHexString(codes);
        //RemoteCore.sendIRCode(codes,codes.length);

    }

    @Override
    public boolean hasLearn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IRCode irCodeReceiver() {
        String data = Tools.readSysFile(ET_IR_LEARN);
//		Log.v(TAG, "data= "+data);
        if(data!=null){
            byte[] learnNewData = Tools.strToIntarray2(data);
            IRCode  ircode = RemoteCore.ET4003Learn(learnNewData);
            //		IRCode ircode = new IRCode("38028,343,172,21,65,21,65,21,65,21,22,21,65,21,65,21,65,21,65,21,22,21,22,21,22,21,65,21,22,21,22,21,22,21,22,21,65,21,22,21,22,21,65,21,22,21,22,21,22,21,22,21,22,21,65,21,65,21,22,21,65,21,65,21,65,21,65,21,1673,343,86,21,3732");
            return ircode;
        }
        return null;
    }

    @Override
    public boolean sendLearnCmd(int cmd) {
        if(cmd == 1){
            RemoteCore.learnIRCodeStart();
        }else {
            RemoteCore.learnIRCodeStop();
        }
        return false;
    }

    @Override
    public boolean getState() {
        // TODO Auto-generated method stub
        String state = Tools.readSysFile(ET_IR_STATE);
//				Logger.debug("et4007 state is "+state);
        if("1".equals(state)){
            return true;
        }else {
            return false;
        }
    }



}
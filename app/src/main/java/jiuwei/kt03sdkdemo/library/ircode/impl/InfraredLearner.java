package jiuwei.kt03sdkdemo.library.ircode.impl;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import jiuwei.kt03sdkdemo.library.bean.IRCode;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredDevice;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredLearner;
import jiuwei.kt03sdkdemo.library.util.ETLogger;
import jiuwei.kt03sdkdemo.library.util.Globals;

public class InfraredLearner implements IInfraredLearner {

	private boolean _run;
	IInfraredDevice mDevice = new DummyIRDevice();
	private Handler handler;
	private IRCodeReceiver codeReceiver;
	private LearnThread lTh;

	public InfraredLearner() {
		super();
		int type = Globals.DEVICE;
	
		switch (type) {
		
		/*case DeviceType.ET4003:
			mDevice = new ET4003IRDevice();
			break;
		case DeviceType.ET4007:
			ETLogger.debug("4007irdevice");
			mDevice = new ET4007IRDevice();
			break;
		default:
			ETLogger.debug("dummyirdevice");
			mDevice = new DummyIRDevice();
			break;*/

		}
		
		 handler = new Handler()
		    {
			 
		      public void handleMessage(Message msg)
		      {
		        switch (msg.what)
		        {
		       
		        case Globals.WHAT_LEARN:
		        	
					Log.v(TAG, String.valueOf( msg.getData().getBoolean("state")));
					if (msg.getData().getBoolean("state")==false){
					IRCode ircode = mDevice.irCodeReceiver();
				
					codeReceiver.onIRCodeReceived(ircode);
					mDevice.sendLearnCmd(0);
					}
		        default:
		        	break;
		        }
		       
		      }
		    };
			ETLogger.debug("Handler start");
		// TODO Auto-generated constructor stub
	}

	

	


	@Override
	public void waitForIRCode(IRCodeReceiver ircodereceiver) {
		// TODO Auto-generated method stub
		codeReceiver = ircodereceiver;
		mDevice.sendLearnCmd(1);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lTh==null){
		 lTh = new LearnThread();
		     lTh.start();
		}
		_run = true;
	}
	private class LearnThread extends Thread {
		boolean status;
		
		
		
		@Override
		public void run() {
			Log.v(TAG, "thread is running");
			  while(_run) {
				Log.v(TAG, "learn is running");
	        	status=	mDevice.getState();
	        	Message msg = new Message();  
	            Bundle bundle = new Bundle();  
	            bundle.putBoolean("state", status);  
	            msg.setData(bundle); 
	            msg.what = Globals.WHAT_LEARN;  
	            handler.sendMessage(msg);  
	            _run = false;
			}
		}
		}

	
}

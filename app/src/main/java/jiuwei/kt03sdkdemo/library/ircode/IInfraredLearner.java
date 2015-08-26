package jiuwei.kt03sdkdemo.library.ircode;


import jiuwei.kt03sdkdemo.library.bean.IRCode;

/**红外信号发送接口
 * */
public interface IInfraredLearner
{
	final static String TAG = "IInfraredSeneder";
	 public  interface IRCodeReceiver
	    {

	        public abstract void onIRCodeReceived(IRCode ircode);
	    }
	/**
	 * 发送信号
	 * */
	
	 public abstract void waitForIRCode(IRCodeReceiver ircodereceiver);
	
	
}

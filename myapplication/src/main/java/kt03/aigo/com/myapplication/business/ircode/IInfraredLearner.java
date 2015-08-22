package kt03.aigo.com.myapplication.business.ircode;


import kt03.aigo.com.myapplication.business.bean.IRCode;

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

package jiuwei.kt03sdkdemo.library.ircode;


import jiuwei.kt03sdkdemo.library.bean.IRCode;

/**底层红外设备接口
 * 
 * 真实开发时替换其实现
 * */
public interface IInfraredDevice
{
	final static String TAG = "IInfraredDevice";
	
	
	/**发送红外数据进行遥控电器*/
	public void sendIr(int freq, int[] data);
	
	public boolean hasLearn();
	
	public IRCode irCodeReceiver();
	
	public boolean sendLearnCmd(int cmd);
	
	public boolean getState();

}

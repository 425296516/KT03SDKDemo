package jiuwei.kt03sdkdemo.library.ircode;

import java.util.List;

import jiuwei.kt03sdkdemo.library.bean.IRKey;
import jiuwei.kt03sdkdemo.library.bean.Infrared;
import jiuwei.kt03sdkdemo.library.bean.Remote;

/**红外信号发送接口
 * */
public interface IInfraredSender
{
	final static String TAG = "IInfraredSeneder";

	/**
	 * 发送信号
	 * @param remote 遥控器数据
	 * @param key 点击的按键数据
	 * */
	public List<Infrared> send(Remote remote, IRKey key);
	
	
	
	
}

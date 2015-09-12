package kt03.aigo.com.myapplication.business.ircode;

import java.util.List;

import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;


/**红外信号发送接口
 * */
public interface IInfraredSender
{
	final static String TAG = "InfraredFetcher";

	/**
	 * 发送信号
	 * @param remote 遥控器数据
	 * @param key 点击的按键数据
	 * */
	public List<Infrared> send(Remote remote, IRKey key);
	



}

package kt03.aigo.com.myapplication.business.ircode;

import java.util.List;

import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;


/**
 * interface : 遥控器信号提取者
 * */
public interface IInfraredFetcher
{
	/**普通遥控器的信号提取（非空调）
	 * @param remote 目标遥控器
	 * @param key 被触发的按键对象
	 * @return List<Infrared> 提取到的信号集合*/
	public List<Infrared> fetchInfrareds(Remote remote, IRKey key);
	
	/**空调遥控器的信号提取
	 * @param remote 目标遥控器
	 * @param key 被触发的按键对象
	 * @param state 空调的当前状态
	 * @return List<Infrared> 提取到的信号集合*/
	public List<Infrared> fetchAirInfrareds(Remote remote, IRKey key, AirRemoteState state);
	
	
	//public List<Infrared> fetchDiyAirInfrareds(Remote remote,IRKey key,AirRemoteState state);
	
	
	
	
	
	
}

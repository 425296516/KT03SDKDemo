/**
 * 
 */
package jiuwei.kt03sdkdemo.library.ircode.impl;

import android.content.Context;
import android.support.v4.util.LongSparseArray;
import android.util.Log;

import com.etek.ircore.RemoteCore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jiuwei.kt03sdkdemo.library.air.AirMode;
import jiuwei.kt03sdkdemo.library.air.AirPower;
import jiuwei.kt03sdkdemo.library.air.AirRemoteState;
import jiuwei.kt03sdkdemo.library.air.AirTemp;
import jiuwei.kt03sdkdemo.library.air.AirTime;
import jiuwei.kt03sdkdemo.library.air.AirWindAmount;
import jiuwei.kt03sdkdemo.library.bean.IRCode;
import jiuwei.kt03sdkdemo.library.bean.IRKey;
import jiuwei.kt03sdkdemo.library.bean.Infrared;
import jiuwei.kt03sdkdemo.library.bean.Remote;
import jiuwei.kt03sdkdemo.library.ircode.AirRemoteStateMananger;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredFetcher;
import jiuwei.kt03sdkdemo.library.util.ETLogger;
import jiuwei.kt03sdkdemo.library.util.KeyType;
import jiuwei.kt03sdkdemo.library.util.RemoteUtils;


/**
 * 信号获取类
 * 
 * @author Administrator
 */
public class InfraredFetcher implements IInfraredFetcher {
	private final String TAG = "InfraredFetcher";

	// 记录ab信号状态
	private Map<String, AB> mRemoteABToggles;

	// 空调DIY按键 信号发送索引记录
	private LongSparseArray<Integer> mDiyAirKeyInfraredIndexCache;

	private Context mContext;

	public InfraredFetcher(Context context) {
		mRemoteABToggles = new HashMap<String, AB>();
		mDiyAirKeyInfraredIndexCache = new LongSparseArray<Integer>();
		this.mContext = context;
	};

	/** 获取按键的信号 */
	public List<Infrared> fetchInfrareds(Remote remote, IRKey key) {
		// 空数据
		if (remote == null || key == null || key.getInfrareds() == null
				|| key.getInfrareds().size() == 0 || remote.getKeys() == null
				|| remote.getKeys().size() == 0) {
			return null;
		}
		// 若是组合空调（DIY的多屏空调或者本地云的协议空调）则抛异常告知
		if (RemoteUtils.isMultiAirRemote(remote)) {
			throw new RuntimeException(
					"the remote is a multi-IR air-condition remote,please call method 'fetchAirInfrareds(Remote,Key,AirRemoteState)' instead to get ir codes..");
		}

		if (key == null || key.getInfrareds() == null) {
			Log.e(TAG, "fetchInfrareds................key==null");
			return null;
		}

		// ETLogger.debug(
		// "fetchInfrareds..............普通按键...........key.getInfrareds().");
		AB ab = mRemoteABToggles.get(key.getRemote_id());
		if (ab == null) {
			ab = AB.A;
			mRemoteABToggles.put(key.getRemote_id(), AB.A);
		}

		if (key.getInfrareds().size() == 1
				|| key.getType() == KeyType.MEMORYKEY
				|| key.getType() == KeyType.MEMORYKEY_ONE
				|| key.getType() == KeyType.MEMORYKEY_TWO) // 信号数量为
															// 1
															// ，返回此信号，且不对AB状态做切换
		{
			// ETLogger.debug( "fetchInfrareds............信号数量为 1 或者 是记忆键");
			return key.getInfrareds();
		} else {
			return filterAbInfrareds(key, ab);
		}
	}

	/** 获取空调按键的信号并调整空调状态 */
	public List<Infrared> fetchAirInfrareds(Remote remote, IRKey key,
			AirRemoteState air_state) {
		if (remote == null || key == null || air_state == null) {
			ETLogger.error(TAG,
                    "fetchAirInfrareds.........参数空....remote==null||key==null||air_state==null");
			return null;
		}
		ETLogger.debug(TAG,
				"fetchAirInfrareds........................................key.getProtocol="
						+ key.getProtocol());
		// if (RemoteUtils.isMultiAirRemote(remote) == false)
		// {
		// return fetchInfrareds(remote, key);
		// }

		if (key.getProtocol() > 0) // 协议空调按键
		{
			AirRemoteStateMananger.getInstance(mContext).fitAirState(air_state,
					key);
			ETLogger.debug("fetchAirInfrareds..............................协议空调按键..........air_state = "
					+ air_state);
			return fetchProtocolAirInfrareds(key, air_state);
		} else
		// DIY的组合空调按键
		{
			Log.w(TAG,
					"fetchAirInfrareds........................DIY的组合空调按键................air_state = "
							+ air_state);

			AirRemoteStateMananger.getInstance(mContext).fitAirState(air_state,
					key);
			return fetchDiyAirInfrareds(remote, key, air_state);
		}
	}

	/** 获取本地云"协议"空调按键的信号并调整空调状态 */
	private List<Infrared> fetchProtocolAirInfrareds(IRKey key,
			AirRemoteState air_state) {
		ETLogger.debug("fetchProtocolAirInfrareds........................................协议空调码获取");

		if (air_state != null) {
			if (air_state.getPower() == AirPower.POWER_OFF
					&& key.getType() != KeyType.POWER) {
				Log.e(TAG,
						"fetchProtocolAirInfrareds.............关键状态点击其他按键 不作响应");
				return null;
			}

			ETLogger.debug("fetchProtocolAirInfrareds............计算信号");

			return loadAirCodes(air_state);
		}
		return null;
	}

	/** 获取DIY的组合空调按键的信号 */
	private List<Infrared> fetchDiyAirInfrareds(Remote remote, IRKey key,
			AirRemoteState air_state) {
		ETLogger.debug("fetchDiyAirInfrareds....##############....DIY空调码获取....air_state = "
				+ new Gson().toJson(air_state));

		if (remote == null || remote.getKeys() == null || key == null
				|| air_state == null) {
			Log.e(TAG, "fetchDiyAirInfrareds........DIY空调码获取..！！！！.数据不完整");

			return null;
		}
		if(remote.getAir_keys()==null){
			Log.e(TAG, "fetchDiyAirInfrareds........DIY空调码码库 ERROR");
			return null;
		}

		if (air_state.getPower() == AirPower.POWER_OFF
				&& key.getType() != KeyType.POWER) {
			return null;
		}

		if (key.getType() != KeyType.POWER) // 如果按钮点击的不是电源键，记录最后按键类型
		{
			air_state.setLast_key(key.getType());
		}

		List<Infrared> infrareds = null;

		Log.v(TAG, "fetchDiyAirInfrareds........DIY空调码获取.....air_state.mode = "
				+ air_state.getMode());

		if (key.getType() == KeyType.POWER || key.getType() == KeyType.MODE
				|| key.getType() == KeyType.TEMP_UP
				|| key.getType() == KeyType.TEMP_DOWN
				|| key.getType() == KeyType.WIND_AMOUNT
				|| key.getType() == KeyType.WIND_HORIZONTAL) {
			String airIndex = RemoteUtils.getAirStrFrom(air_state);
			
			infrareds= getRemoteAirKeyDB(remote,airIndex);
//				ETLogger.debug(infrareds.get(0).irString());
			
		}

		return infrareds;
	}
	
	List<Infrared> getRemoteAirKeyDB(Remote remote, String value){
		for (IRKey key :remote.getAir_keys()){
//			ETLogger.debug(value+"_"+ key.getName());
			if(key.getName().equalsIgnoreCase(value)){
				return key.getInfrareds();
			}
		}
		return null;
		
	}

	/**
	 * 获取指定遥控器的调整到指定状态的信号
	 * 
	 * @param state
	 *            当前空调遥控器的状态
	 * @param power
	 *            目标“开关”状态
	 * @param mode
	 *            目标模式
	 * @param wind_amount
	 *            目标风量
	 * @param temp
	 *            目标温度
	 * */
	public List<Infrared> fetchAirInfrareds(Remote remote,
			AirRemoteState state, AirPower power, AirMode mode,
			AirWindAmount wind_amount, AirTemp temp) {
		ETLogger.debug("fetchAirInfrareds...........power = " + power
				+ ",mode = " + mode + ", wind_amount = " + wind_amount
				+ ", temp = " + temp);

		if (state == null) {
			return null;
		}
		boolean isProtocoRemote = RemoteUtils.isProtocolAirRemote(remote);

		state.setPower(power);
		state.setMode(mode);
		state.setWind_amount(wind_amount);
		if (temp != null) {
			state.setTemp(temp);
		} else {
			state.setTemp(AirTemp.T26);
		}

		int clickKeyType = 0; // 激发的按键
		if (power == AirPower.POWER_ON) // 开机状态信号，寻找相应状态信号
		{
			if (isProtocoRemote
					&& (mode == AirMode.COOL || mode == AirMode.HOT)) {
				clickKeyType = KeyType.TEMP_UP;
			} else {
				clickKeyType = KeyType.MODE;
			}
		} else
		// 关机状态
		{
			clickKeyType = KeyType.POWER;
		}

		state.setCurrent_key(clickKeyType);

		IRKey clickKey = null;
		// 寻找触发按钮
		for (IRKey tmpKey : remote.getKeys()) {
			if (tmpKey != null && tmpKey.getType() == clickKeyType) {
				state.setProtocol(tmpKey.getProtocol());
				clickKey = tmpKey;
				break;
			}
		}

//		Log.w(TAG, "fetchAirInfrareds..........获取指定遥控器的调整到指定状态的信号....state = "
//				+ state);
//		Log.e(TAG,
//				"fetchAirInfrareds..........获取指定遥控器的调整到指定状态的信号....clickKey = "
//						+ ETJSON.toJSONString(clickKey));

		if (isProtocoRemote) {
			return loadAirCodes(state);
		} else {
			return fetchDiyAirInfrareds(remote, clickKey, state);
		}
	}

	/**
	 * 获取定时信号
	 * 
	 * @param key
	 *            按键对象
	 * @param time_mins
	 *            定时时间 （分钟）
	 * @param air_state
	 *            空调状态
	 * */
	public List<Infrared> fetchAirTimeInfrareds(IRKey key, int time_mins,
			AirRemoteState air_state) {
		if (key == null || time_mins <= 0 || air_state == null) {
			return null;
		}
		air_state.setProtocol(key.getProtocol());
		air_state.setCurrent_key(KeyType.AIR_TIME);
		air_state.setTime(AirTime.TIME_ON);
		air_state.setTime_limit(time_mins);

		Log.w(TAG,
                "获取定时信号 ...air_state.Time_limit = " + air_state.getTime_limit()
                        + "....air_state -> " + new Gson().toJson(air_state));

		return loadAirCodes(air_state);
	}

	/** 通过记录的索引获取信号 */
	private List<Infrared> filterInfraredsByIndex(IRKey key) {
		ETLogger.debug("filterInfraredsByIndex.........通过记录的索引获取信号");

		Integer key_infrared_index = mDiyAirKeyInfraredIndexCache.get(key
				.getId());
		if (key_infrared_index == null) {
			Log.w(TAG,
					"filterInfraredsByIndex.........通过记录的索引获取信号.....初始化此按键的索引");

			mDiyAirKeyInfraredIndexCache.put(key.getId(), 0);
			key_infrared_index = 0;
		} else {
			Log.i(TAG, "filterInfraredsByIndex.........通过记录的索引获取信号.....index++");

			key_infrared_index++;
		}

		ETLogger.debug("filterInfraredsByIndex.........通过记录的索引获取信号.....index = "
				+ key_infrared_index);

		List<Infrared> infrareds = new ArrayList<Infrared>();

		if (key_infrared_index >= key.getInfrareds().size()) {
			Log.w(TAG,
					"filterInfraredsByIndex....通过记录的索引获取信号.....到底后归零....index = "
							+ key_infrared_index + ",key.infrareds.size="
							+ key.getInfrareds().size());

			key_infrared_index = 0;
		}

		infrareds.add(key.getInfrareds().get(key_infrared_index));

		// 重新设置索引

		mDiyAirKeyInfraredIndexCache.put(key.getId(), key_infrared_index);

		return infrareds;
	}

	/** 过滤出AB信号 */
	/** 根据AB状态寻找下一状态的信号，并调整状态 */
	private List<Infrared> filterAbInfrareds(IRKey key, AB ab) {
		ETLogger.debug("filterAbInfrareds.........寻找下一状态的信号....当前是 AB状态为 -> "
				+ ab);

		List<Infrared> infrareds = new ArrayList<Infrared>();

		for (Infrared ir : key.getInfrareds()) {
			if (ir != null) {
				switch (ab) {
				case A:
					if ("B".equals(ir.getMark())) {
						infrareds.add(ir);
						ETLogger.debug("filterAbInfrareds.............当前是 AB状态为 -> A......找到B信号，加入结果集....ir = "
								+ new Gson().toJson(ir));
					}
					break;

				case B:
					if ("A".equals(ir.getMark())) {
						infrareds.add(ir);
						Log.v(TAG,
								"filterAbInfrareds.............当前是 AB状态为 -> B......找到A信号，加入结果集....ir = "
										+ new Gson().toJson(ir));
					}
					break;

				default:
					break;
				}
			}
		}

		if (infrareds.size() > 0) {
			Log.w(TAG, "filterAbInfrareds.............寻找AB信号成功，切换AB标志....当前为  "
					+ ab + ",切换 为" + (ab == AB.A ? AB.B : AB.A));

			mRemoteABToggles.put(key.getRemote_id(), ab == AB.A ? AB.B : AB.A);
			return infrareds;
		} else {
			return key.getInfrareds();
		}
	}

	/** 确认DIY键是否包含有效信号 */
	private boolean validateDiyAirKey(IRKey key) {
		if (key == null || key.getInfrareds() == null) {
			return false;
		}

		for (Infrared ir : key.getInfrareds()) {
			if (ir == null || ir.getData() == null) {
				continue;
			}
			switch (key.getType()) {
			case KeyType.POWER:
				if (ir.getFunc() == 0 || ir.getFunc() == 1) {
					return true;
				}
				break;

			case KeyType.MODE:
				if (ir.getFunc() == 0 || ir.getFunc() == 1 || ir.getFunc() == 2
						|| ir.getFunc() == 3 || ir.getFunc() == 4) {
					return true;
				}
				break;

			case KeyType.TEMP_UP:
			case KeyType.TEMP_DOWN:
				if (ir.getFunc() == 3 || ir.getFunc() == 4) {
					if (ir.getMark() >= 16 && ir.getMark() <= 31) {
						return true;
					}
				}
				break;

			case KeyType.WIND_AMOUNT:
				if (ir.getFunc() == 0 || ir.getFunc() == 1 || ir.getFunc() == 2
						|| ir.getFunc() == 3 || ir.getFunc() == 4) {
					return true;
				}
				break;

			case KeyType.WIND_HORIZONTAL:

				break;

			case KeyType.WIND_VERTICAL:

				break;

			case KeyType.AIR_WIND_DIRECT:

			default:
				break;
			}
		}
		return false;
	}

	/** 获取高一度的温度枚举 */
	private AirTemp getPlusTemp(AirRemoteState state) {
		if (state == null) {
			return AirTemp.T31;
		} else {
			return getPlusTemp(state.getTemp());
		}
	}

	/** 获取高一度的温度枚举 */
	private AirTemp getPlusTemp(AirTemp temp) {
		if (temp == null) {
			return AirTemp.T31;
		}
		AirTemp plusTmp = null;
		switch (temp) {
		case T16:
			plusTmp = AirTemp.T17;
			break;

		case T17:
			plusTmp = AirTemp.T18;
			break;

		case T18:
			plusTmp = AirTemp.T19;
			break;

		case T19:
			plusTmp = AirTemp.T20;
			break;

		case T20:
			plusTmp = AirTemp.T21;
			break;

		case T21:
			plusTmp = AirTemp.T22;
			break;

		case T22:
			plusTmp = AirTemp.T23;
			break;

		case T23:
			plusTmp = AirTemp.T24;
			break;

		case T24:
			plusTmp = AirTemp.T25;
			break;

		case T25:
			plusTmp = AirTemp.T26;
			break;

		case T26:
			plusTmp = AirTemp.T27;
			break;

		case T27:
			plusTmp = AirTemp.T28;
			break;

		case T28:
			plusTmp = AirTemp.T29;
			break;

		case T29:
			plusTmp = AirTemp.T30;
			break;

		case T30:
			plusTmp = AirTemp.T31;
			break;

		case T31:
		default:
			plusTmp = AirTemp.T31;
			break;
		}
		return plusTmp;
	}

	/** 获取低一度的温度枚举 */
	private AirTemp getReduceTemp(AirRemoteState state) {
		if (state == null) {
			return AirTemp.T17;
		} else {
			return getReduceTemp(state.getTemp());
		}
	}

	/** 获取低一度的温度枚举 */
	private AirTemp getReduceTemp(AirTemp temp) {
		if (temp == null) {
			return AirTemp.T17;
		}
		AirTemp reduceTmp = null;
		switch (temp) {
		case T31:
			reduceTmp = AirTemp.T30;
			break;

		case T30:
			reduceTmp = AirTemp.T29;
			break;

		case T29:
			reduceTmp = AirTemp.T28;
			break;

		case T28:
			reduceTmp = AirTemp.T27;
			break;

		case T27:
			reduceTmp = AirTemp.T26;
			break;

		case T26:
			reduceTmp = AirTemp.T25;
			break;

		case T25:
			reduceTmp = AirTemp.T24;
			break;

		case T24:
			reduceTmp = AirTemp.T23;
			break;

		case T23:
			reduceTmp = AirTemp.T22;
			break;

		case T22:
			reduceTmp = AirTemp.T21;
			break;

		case T21:
			reduceTmp = AirTemp.T20;
			break;

		case T20:
			reduceTmp = AirTemp.T19;
			break;

		case T19:
			reduceTmp = AirTemp.T18;
			break;

		case T18:
			reduceTmp = AirTemp.T17;
			break;

		case T17:
			reduceTmp = AirTemp.T16;
			break;

		case T16:
		default:
			reduceTmp = AirTemp.T16;
			break;
		}
		return reduceTmp;
	}

	/** 获取协议空调的当前状态信号对象 */
	public List<Infrared> loadAirCodes(AirRemoteState air_state) {
		if (air_state == null) {
			return null;
		}
		List<Infrared> infrareds = new ArrayList<Infrared>();

		int[] state = new int[19];
		/**
		 * AirPower power;// 开关 AirMode mode;// 空调模式 AirTemp temp;// 温度
		 * AirWindAmount wind_amount;// 风量 AirWindDirection wind_direction;// 风向
		 * AirWindHoz wind_hoz; // 水平风向 AirWindVer wind_ver; // 垂直风向
		 * 
		 * // 其他状态 AirSuper super_mode; // 强力 AirSleep sleep;// 睡眠 AirAidHot
		 * hot;// 辅热 AirTime time;// 定时 AirTempDisplay temp_display;//温度显示
		 * 
		 * AirPowerSaving power_saving;// 节能模式 AirAnion anion;// 负离子 AirComfort
		 * comfort;// 舒适 AirFlashAir flash_air;// 清新、换气 AirLight light;// 灯光
		 * AirWet wet;// 加湿 AirMute mute;//静音
		 */

		state[0] = (int) air_state.getProtocol();
		state[1] = (int) air_state.getPower().value();
		state[2] = (int) air_state.getMode().value();
		state[4] = (int) air_state.getTemp().value();
		state[3] = (int) air_state.getWind_amount().value();
		if (air_state.getWind_direction() != null) {
			state[5] = (byte) air_state.getWind_direction().value();
			state[6] = 0;

		} else {
			state[5] = 0;
			state[6] = (byte) air_state.getWind_hoz().value();

		}
		// state[6] = (byte) air_state.getWind_ver().value();
		state[7] = getAirKey(air_state.getCurrent_key());

		// state[7] = (byte) air_state.getSuper_mode().value();
		state[8] = (byte) air_state.getSleep().value();
		state[9] = (byte) air_state.getHot().value();
		state[10] = (byte) air_state.getTime().value();
		state[11] = (byte) air_state.getTemp_display().value();
		state[12] = (byte) air_state.getPower_saving().value();
		state[13] = (byte) air_state.getAnion().value();
		state[14] = (byte) air_state.getComfort().value();
		state[15] = (byte) air_state.getFlash_air().value();
		state[16] = (byte) air_state.getLight().value();
		state[17] = (byte) air_state.getWet().value();
		state[18] = (byte) air_state.getMute().value();

		byte[] airCode = RemoteCore.getProntoAirData(state);
		IRCode ir = RemoteCore.ETcodeToPronto(airCode);
		Infrared inf = new Infrared();
		inf.setInfrared(ir);
		infrareds.add(inf);
		// Infrared ir =
		// LocalIrDb.getIrDb(mContext).getAirCode(air_state.getProtocol(),
		// air_state.getCurrent_key(), air_state.getCaculate_number(),
		// air_state.getTime_limit(), state);
		// infrareds.add(ir);
		// if (air_state.getCurrent_key() == KeyType.POWER &&
		// air_state.getPower() == AirPower.POWER_ON && air_state.getLast_key()
		// >0) // 如果当前键是开机键，则需要生成上次关机前的信号
		// {
		// air_state.setCurrent_key(air_state.getLast_key());
		// Log.v(TAG, "loadAirCode.....############..生成上次关机前的信号.....last_key = "
		// + air_state.getLast_key() + ",current_key = " +
		// air_state.getCurrent_key() + ",caculate_number = " +
		// air_state.getCaculate_number() + ",cime_limit = " +
		// air_state.getTime_limit());
		// air_state.setCaculate_number(air_state.getCaculate_number() + 1);
		// Infrared last_ir =
		// LocalIrDb.getIrDb(mContext).getAirCode(air_state.getProtocol(),
		// air_state.getCurrent_key(), air_state.getCaculate_number(),
		// air_state.getTime_limit(), state);
		// infrareds.add(last_ir);
		// }

		state = null;
		ETLogger.debug("loadAirCode.....############.....获取到的信号有 ——> "
				+ new Gson().toJson(infrareds));
		return infrareds;
	}

	/**
	 * 取得目标温度和温度按键中包含的温度最小差
	 * 
	 * @param state
	 *            目标状态，包含温度
	 * @param tempKey
	 *            温度按键（温度+或温度-）
	 * @return -1 表示无法比较，0则表示温度键包含目标温度
	 * */
	private int diffTemp(AirRemoteState state, IRKey tempKey) {
		if (state == null || state.getTemp() == null || state.getMode() == null
				|| tempKey == null || tempKey.getInfrareds() == null
				|| tempKey.getInfrareds().size() == 0) {
			return -1;
		}
		int minDiff = -1;
		for (Infrared ir : tempKey.getInfrareds()) {
			if (ir != null && ir.getFunc() == state.getMode().value()
					&& ir.getMark() > 0) {
				try {
					int diff = Math.abs(ir.getMark() - state.getTemp().value());

					if (minDiff == -1 || minDiff > diff) {
						minDiff = diff;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return minDiff;
	}

	/**
	 * 取得与目标温度最接近的温度信号
	 * */
	private Infrared fetchMinDiffTempInfrared(AirRemoteState state,
			Remote remote) {
		ETLogger.debug("fetchMinDiffTempInfrared.....state = "
				+ new Gson().toJson(state));

		if (state == null || state.getTemp() == null || state.getMode() == null
				|| remote == null || remote.getKeys() == null
				|| remote.getKeys().size() == 0) {
			return null;
		}

		Infrared minDiffInfrared = null;
		int minDiff = -1;

		for (IRKey key : remote.getKeys()) {
			if (key != null && key.getInfrareds() != null) {
				for (Infrared ir : key.getInfrareds()) {
					if (ir != null && ir.getFunc() == state.getMode().value()
							&& ir.getMark() > 0) {
						try {
							int diff = Math.abs(ir.getMark()
									- state.getTemp().value());

							Log.v(TAG,
									"fetchMinDiffTempInfrared..........ir.getMark() = "
											+ ir.getMark()
											+ ", state.getTemp() = "
											+ state.getTemp() + ",diff = "
											+ diff);

							if (minDiff == -1 || minDiff >= diff) {
								minDiff = diff;
								minDiffInfrared = ir;
								ETLogger.debug("fetchMinDiffTempInfrared..........符合替换最佳信号条件....");
							} else {
								Log.w(TAG,
										"fetchMinDiffTempInfrared..........不符合替换最佳信号条件....");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (minDiffInfrared != null) {
			ETLogger.debug("fetchMinDiffTempInfrared.....找到的最佳信号  minDiffInfrared = "
					+ new Gson().toJson(minDiffInfrared));
			try {
				state.setTemp(AirTemp.getAirTemp(minDiffInfrared.getMark()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return minDiffInfrared;
	}

	/** AB信号标志 */
	private enum AB {
		A, B;
	}

	int getAirKey(int key) {
		int k = 0;
		switch (key) {
		case KeyType.POWER:
			k = 0;
			break;
		case KeyType.MODE:
			k = 1;
			break;
		case KeyType.TEMP_UP:
			k = 3;
			break;
		case KeyType.TEMP_DOWN:
			k = 4;
			break;
		case KeyType.WIND_AMOUNT:
			k = 2;
			break;
		case KeyType.WIND_HORIZONTAL:
			k = 5;
			break;
		case KeyType.WIND_VERTICAL:
			k = 6;
			break;
		default:
			k = 7;
			break;
		}
		return k;

	}
}

package jiuwei.kt03sdkdemo.library.ircode;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jiuwei.kt03sdkdemo.library.air.AirAidHot;
import jiuwei.kt03sdkdemo.library.air.AirAnion;
import jiuwei.kt03sdkdemo.library.air.AirComfort;
import jiuwei.kt03sdkdemo.library.air.AirFlashAir;
import jiuwei.kt03sdkdemo.library.air.AirLight;
import jiuwei.kt03sdkdemo.library.air.AirMode;
import jiuwei.kt03sdkdemo.library.air.AirMute;
import jiuwei.kt03sdkdemo.library.air.AirPower;
import jiuwei.kt03sdkdemo.library.air.AirPowerSaving;
import jiuwei.kt03sdkdemo.library.air.AirRemoteState;
import jiuwei.kt03sdkdemo.library.air.AirSleep;
import jiuwei.kt03sdkdemo.library.air.AirSuper;
import jiuwei.kt03sdkdemo.library.air.AirTemp;
import jiuwei.kt03sdkdemo.library.air.AirTempDisplay;
import jiuwei.kt03sdkdemo.library.air.AirTime;
import jiuwei.kt03sdkdemo.library.air.AirWet;
import jiuwei.kt03sdkdemo.library.air.AirWindAmount;
import jiuwei.kt03sdkdemo.library.air.AirWindDirection;
import jiuwei.kt03sdkdemo.library.air.AirWindHoz;
import jiuwei.kt03sdkdemo.library.air.AirWindVer;
import jiuwei.kt03sdkdemo.library.bean.IRKey;
import jiuwei.kt03sdkdemo.library.bean.Infrared;
import jiuwei.kt03sdkdemo.library.util.ETLogger;
import jiuwei.kt03sdkdemo.library.util.KeyType;

public class AirRemoteStateMananger
{
	private static final String TAG = AirRemoteStateMananger.class.getName();
	
	//状态持久保持的xml名称 （由SharePreference保持）
	private final static String NAME_AIR_REMOTE_STATES_CACHE = "air_remote_sates_cache";
	private static AirRemoteStateMananger mStateMananger; 
	
	private Map<String,AirRemoteState> mRemoteStateCache; //空调状态缓存
	
	private SharedPreferences mSharedPreferences;
	
	
	Context mContext;
	private AirRemoteStateMananger(Context context)
	{
		this.mContext = context;
		mRemoteStateCache = new HashMap<String, AirRemoteState>();
		mSharedPreferences = mContext.getSharedPreferences(NAME_AIR_REMOTE_STATES_CACHE, Context.MODE_PRIVATE);
	}
	
	public static synchronized AirRemoteStateMananger getInstance(Context context)
	{
		if(mStateMananger==null)
		{
			mStateMananger = new AirRemoteStateMananger(context);
		}
		return mStateMananger;
	}
	
	/**获取指定空调遥控器id的状态数据*/
	public AirRemoteState getAirRemoteState(String remote_id)
	{
		if(remote_id==null||remote_id.trim().equals(""))
		{
			return null;
		}
		AirRemoteState state = mRemoteStateCache.get(remote_id);
		if(state!=null)
		{
			return state;
		}
		
		//生成新的
		state = new AirRemoteState(remote_id);
		mRemoteStateCache.put(remote_id, state);
		//保存
		saveAirRemoteState(state);
		
		return state;
	}
	
	/**保存状态*/
	private void saveAirRemoteState(AirRemoteState state)
	{
		if(state==null)
		{
			return;
		}
		mSharedPreferences.edit().putString(state.getRemote_id(), new Gson().toJson(state)).commit();
	}
	
	
	/*空调遥控器状态调整*/
	public void fitAirState(AirRemoteState state,IRKey key)
	{
		ETLogger.debug(TAG, "fitAirstate");
		if(key==null||state==null)
		{
			ETLogger.error(TAG, "fitAirstate quit");
			return;
		}
		state.setProtocol(key.getProtocol());
		if (state.getCurrent_key() != KeyType.POWER) // 不是电源键的时候，记录最后的点击按钮
		{
			state.setLast_key(state.getCurrent_key());
		}
		
		state.setCurrent_key(key.getType());
		switch (key.getType())
		{
		case KeyType.POWER:
			ETLogger.debug(TAG, "power key");
			if (state.getPower() == AirPower.POWER_OFF)
			{
				state.setPower(AirPower.POWER_ON);
			}
			else
			{
				state.setPower(AirPower.POWER_OFF);
			}
			// 重置定时状态
			state.setTime(AirTime.TIME_OFF);
			
			break;

		case KeyType.MODE:
			if (state.getMode() == AirMode.AUTO)
			{
				state.setMode(AirMode.COOL);
			}
			else if (state.getMode()  == AirMode.COOL)
			{
				state.setMode( AirMode.DRY);
			}
			else if (state.getMode()  == AirMode.DRY)
			{
				state.setMode(AirMode.HOT);
			}
			else if (state.getMode()  == AirMode.HOT)
			{
				state.setMode(AirMode.WIND);
			}
			else
			{
				state.setMode(AirMode.AUTO);
			}
			break;

		case KeyType.MUTE:
			if (state.getMute() == AirMute.MUTE_OFF)
			{
				state.setMute(AirMute.MUTE_ON);
			}
			else
			{
				state.setMute(AirMute.MUTE_OFF);
			}

			break;

		case KeyType.WIND_AMOUNT: // 协议空调，没有Level_4风量
			if (state.getWind_amount() == AirWindAmount.AUTO)
			{
				state.setWind_amount(AirWindAmount.LEVEL_1);
			}
			else if (state.getWind_amount() == AirWindAmount.LEVEL_1)
			{
				state.setWind_amount(AirWindAmount.LEVEL_2);
			}
			else if (state.getWind_amount() == AirWindAmount.LEVEL_2)
			{
				state.setWind_amount(AirWindAmount.LEVEL_3);
			}
			else
			{
				state.setWind_amount(AirWindAmount.AUTO);
			}
			break;

		case KeyType.WIND_HORIZONTAL:
			if (state.getWind_hoz() == AirWindHoz.HOZ_OFF)
			{
				state.setWind_hoz(AirWindHoz.HOZ_ON);
			}
			else
			{
				state.setWind_hoz(AirWindHoz.HOZ_OFF);
			}
			break;

		case KeyType.WIND_VERTICAL:
			if (state.getWind_ver() == AirWindVer.VER_OFF)
			{
				state.setWind_ver(AirWindVer.VER_ON);
			}
			else
			{
				state.setWind_ver( AirWindVer.VER_OFF);
			}
			break;

		case KeyType.TEMP_UP:
			AirTemp maxTemp = getAirMaxTemp(key, state);
			int up_value = state.getTemp().value() + 1;
			if (up_value > maxTemp.value())
			{
				state.setTemp(maxTemp);
			}
			else
			{
				state.setTemp(AirTemp.getAirTemp(up_value));
			}
			break;

		case KeyType.TEMP_DOWN:
			AirTemp minTemp = getAirMinTemp(key, state);

			int down_value = state.getTemp().value() - 1;
			if (down_value < minTemp.value())
			{
				state.setTemp( minTemp);
			}
			else
			{
				state.setTemp(AirTemp.getAirTemp(down_value));
			}
			break;

		case KeyType.AIR_AID_HOT:
			if (state.getHot() == AirAidHot.AIDHOT_OFF)
			{
				state.setHot(AirAidHot.AIDHOT_ON);
			}
			else
			{
				state.setHot(AirAidHot.AIDHOT_OFF);
			}
			break;

		case KeyType.AIR_ANION:
			if (state.getAnion() == AirAnion.ANION_OFF)
			{
				state.setAnion(AirAnion.ANION_ON);
			}
			else
			{
				state.setAnion(AirAnion.ANION_OFF);
			}
			break;

		case KeyType.AIR_COMFORT:
			if (state.getComfort() == AirComfort.COMFORT_OFF)
			{
				state.setComfort(AirComfort.COMFORT_ON);
			}
			else
			{
				state.setComfort(AirComfort.COMFORT_OFF);
			}
			break;

		case KeyType.AIR_FLASH_AIR:
			if (state.getFlash_air() == AirFlashAir.FLASH_OFF)
			{
				state.setFlash_air(AirFlashAir.FLASH_ON);
			}
			else
			{
				state.setFlash_air(AirFlashAir.FLASH_OFF);
			}
			break;

		case KeyType.AIR_LIGHT:
			if (state.getLight() == AirLight.LIGHT_OFF)
			{ 
				state.setLight(AirLight.LIGHT_ON);
			}
			else
			{
				state.setLight(AirLight.LIGHT_OFF);
			}
			break;

		case KeyType.AIR_POWER_SAVING:
			if (state.getPower_saving() == AirPowerSaving.POWER_SAVING_OFF)
			{
				state.setPower_saving(AirPowerSaving.POWER_SAVING_ON);
			}
			else
			{
				state.setPower_saving(AirPowerSaving.POWER_SAVING_OFF);
			}
			break;

		case KeyType.AIR_SLEEP:
			if (state.getSleep() == AirSleep.SLEEP_OFF)
			{
				state.setSleep(AirSleep.SLEEP_ON);
			}
			else
			{
				state.setSleep(AirSleep.SLEEP_OFF);
			}
			break;

		case KeyType.AIR_SUPER:
			if (state.getSuper_mode() == AirSuper.SUPER_OFF)
			{
				state.setSuper_mode(AirSuper.SUPER_ON);
			}
			else
			{
				state.setSuper_mode(AirSuper.SUPER_OFF);
			}
			break;

		case KeyType.AIR_TEMP_DISPLAY:
			if (state.getTemp_display() == AirTempDisplay.DISPLAY_INDOOR_TEMP)
			{
				state.setTemp_display(AirTempDisplay.DISPLAY_OUTDOOR_TEMP);
			}
			else if (state.getTemp_display() == AirTempDisplay.DISPLAY_OUTDOOR_TEMP)
			{
				state.setTemp_display(AirTempDisplay.DISPLAY_TARGET_TEMP);
			}
			else if(state.getTemp_display() == AirTempDisplay.DISPLAY_TARGET_TEMP)
			{
				state.setTemp_display(AirTempDisplay.DISPLAY_NONE);
			}
			else if(state.getTemp_display() == AirTempDisplay.DISPLAY_NONE)
			{
				state.setTemp_display( AirTempDisplay.DISPLAY_INDOOR_TEMP);
			}
			else
			{
				state.setTemp_display(AirTempDisplay.DISPLAY_NONE);
			}
			
			break;

		case KeyType.AIR_TIME:
			state.setTime(AirTime.TIME_ON);
			break;

		case KeyType.AIR_WET:
			if (state.getWet() == AirWet.WET_OFF)
			{
				state.setWet(AirWet.WET_ON);
			}
			else
			{
				state.setWet( AirWet.WET_OFF);
			}
			break;

		case KeyType.AIR_WIND_DIRECT:
			if (state.getWind_direction() == AirWindDirection.AUTO)
			{
				state.setWind_direction(AirWindDirection.UP);
			}
			else if (state.getWind_direction() == AirWindDirection.UP)
			{
				state.setWind_direction( AirWindDirection.MIDDLE);
			}
			else if (state.getWind_direction() == AirWindDirection.MIDDLE)
			{
				state.setWind_direction(AirWindDirection.DOWN);
			}
			else
			{
				state.setWind_direction(AirWindDirection.AUTO);
			}
			break;

		case KeyType.AIR_QUICK_COOL: // 切换到“一键冷”状态
			quick_cool(state);
			break;

		case KeyType.AIR_QUICK_HOT: // 切换到“一键热”状态
			quick_hot(state);
			break;

		default:
			break;
		}

		if (key.getType() != KeyType.AIR_TIME)
		{
			state.setTime(AirTime.TIME_OFF);
			state.setTime_limit(0);
		}
		if (state.getTime_limit() > 0)
		{
			state.setTime_limit(0);
		}

		state.setCaculate_number(state.getCaculate_number()+1);
		ETLogger.debug(TAG,"airRemoteState = "+state.getPower()+"_"+state.getRemote_id()+"_"+state.getTemp()+"_"+state.getTemp_display());

		saveAirRemoteState(state);
	}

	/** 调整到“一键冷”状态 */
	private void quick_cool(AirRemoteState state)
	{
		state.setMode(AirMode.COOL);
		state.setTemp(AirTemp.T26);
		state.setWind_amount(AirWindAmount.AUTO);
		state.setWind_direction(AirWindDirection.MIDDLE);
		state.setWind_hoz(AirWindHoz.HOZ_OFF);
		state.setWind_ver(AirWindVer.VER_OFF);

		state.setSuper_mode(AirSuper.SUPER_OFF);
		state.setSleep(AirSleep.SLEEP_OFF);
		state.setHot(AirAidHot.AIDHOT_OFF);
		state.setTime(AirTime.TIME_OFF);
		state.setTemp_display(AirTempDisplay.DISPLAY_INDOOR_TEMP);

		state.setPower_saving(AirPowerSaving.POWER_SAVING_OFF);
		state.setAnion(AirAnion.ANION_OFF);
		state.setComfort(AirComfort.COMFORT_OFF);
		state.setFlash_air(AirFlashAir.FLASH_OFF);
		state.setLight(AirLight.LIGHT_ON);
		state.setWet(AirWet.WET_OFF);
		state.setMute(AirMute.MUTE_OFF);
		state.setCaculate_number(state.getCaculate_number()+1);
	}

	/** 调整到“一键热”状态 */
	private void quick_hot(AirRemoteState state)
	{
		state.setMode(AirMode.HOT);
		state.setTemp(AirTemp.T22);
		
		state.setWind_amount(AirWindAmount.AUTO);
		state.setWind_direction(AirWindDirection.MIDDLE);
		state.setWind_hoz(AirWindHoz.HOZ_OFF);
		state.setWind_ver(AirWindVer.VER_OFF);

		state.setSuper_mode(AirSuper.SUPER_OFF);
		state.setSleep(AirSleep.SLEEP_OFF);
		state.setHot(AirAidHot.AIDHOT_OFF);
		state.setTime(AirTime.TIME_OFF);
		state.setTemp_display(AirTempDisplay.DISPLAY_INDOOR_TEMP);

		state.setPower_saving(AirPowerSaving.POWER_SAVING_OFF);
		state.setAnion(AirAnion.ANION_OFF);
		state.setComfort(AirComfort.COMFORT_OFF);
		state.setFlash_air(AirFlashAir.FLASH_OFF);
		state.setLight(AirLight.LIGHT_ON);
		state.setWet(AirWet.WET_OFF);
		state.setMute(AirMute.MUTE_OFF);
		state.setCaculate_number(state.getCaculate_number()+1);

		state.setCaculate_number(state.getCaculate_number()+1);
	}
	
	
	/**获取温度+-按键的最高温度*/
	public AirTemp getAirMaxTemp(IRKey key,AirRemoteState state)
	{
		if(key==null||key.getInfrareds()==null||key.getInfrareds().size()==0||key.getProtocol()>0)
		{
			return AirTemp.T30;
		}
		
		if(key.getType()!=KeyType.TEMP_DOWN&&key.getType()!=KeyType.TEMP_UP)
		{
			return AirTemp.T30;
		}
		int max_tmp = 0;
		for(Infrared ir:key.getInfrareds())
		{
			try
			{
				if (ir != null && ir.getFunc() == state.getMode().value() && ir.getMark() >0)
				{
					int tmp = Integer.valueOf(ir.getMark());
					if(tmp>max_tmp)
					{
						max_tmp=tmp;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return AirTemp.getAirTemp(max_tmp);
	}
	
	/**获取温度+-按键的最低温度*/
	public AirTemp getAirMinTemp(IRKey key,AirRemoteState state)
	{
		if(key==null||key.getInfrareds()==null||key.getInfrareds().size()==0||state==null||key.getProtocol()>0)
		{
			Log.e(TAG, "getAirMinTemp..........获取温度+-按键的最低温度.........key==null||key.getInfrareds()==null||key.getInfrareds().size()==0||state==null||key.getProtocol()>0");
			return AirTemp.T16;
		}
		
		if(key.getType()!=KeyType.TEMP_DOWN&&key.getType()!=KeyType.TEMP_UP)
		{
			Log.e(TAG, "getAirMinTemp..........获取温度+-按键的最低温度........不是TEMP_DOWN或TEMP_UP");
			return AirTemp.T16;
		}
		int min_tmp = 31;
		for(Infrared ir:key.getInfrareds())
		{
			try
			{
				if (ir != null && ir.getFunc() == state.getMode().value() && ir.getMark() > 0)
				{
					int tmp = ir.getMark();
					if(tmp<min_tmp)
					{
						min_tmp=tmp;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		Log.w(TAG, "getAirMinTemp..........获取温度+-按键的最低温度........min_tmp = "+min_tmp);

		return AirTemp.getAirTemp(min_tmp);
	}
	
	
	/**退出App时调用，以保存空调的使用状态*/
	public void flush()
	{
		if(mRemoteStateCache==null||mRemoteStateCache.size()==0)
		{
			return;
		}
		Collection<AirRemoteState> states = mRemoteStateCache.values();
		for(AirRemoteState state:states)
		{
			this.saveAirRemoteState(state);
		}
		
		mRemoteStateCache.clear();
		mRemoteStateCache=null;
		mSharedPreferences=null;
		mStateMananger=null;
	}
	
}

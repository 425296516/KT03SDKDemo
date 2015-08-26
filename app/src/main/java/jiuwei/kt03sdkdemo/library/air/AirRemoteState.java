/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/**
 * @author jiangs
 */

public class AirRemoteState {
	AirRemoteState() {
	}

	public AirRemoteState(String remote_id) {
		if (remote_id == null) {
			throw new NullPointerException(
					"the string param 'remote_id' can not be null");
		}

		this.remote_id = remote_id;
		power = AirPower.POWER_OFF;
		this.mode = AirMode.AUTO;
		this.temp = AirTemp.T26;
		this.wind_amount = AirWindAmount.AUTO;
		this.wind_direction = AirWindDirection.AUTO;
		wind_hoz = AirWindHoz.HOZ_OFF;
		wind_ver = AirWindVer.VER_OFF;

		this.super_mode = AirSuper.SUPER_OFF;
		this.sleep = AirSleep.SLEEP_OFF;
		this.hot = AirAidHot.AIDHOT_OFF;
		this.time = AirTime.TIME_OFF;
		this.temp_display = AirTempDisplay.DISPLAY_NONE;

		this.power_saving = AirPowerSaving.POWER_SAVING_OFF;
		this.anion = AirAnion.ANION_OFF;
		this.comfort = AirComfort.COMFORT_OFF;
		this.flash_air = AirFlashAir.FLASH_OFF;
		this.light = AirLight.LIGHT_ON;
		this.wet = AirWet.WET_OFF;
		this.mute = AirMute.MUTE_OFF;
		this.caculate_number = 0;
	}

	String remote_id;

	int protocol;

	// 空调主要状态
	int current_key; // 触发的按钮

	int last_key;

	int caculate_number;// 本次遥控器打开后，是第几次点击按钮

	AirPower power;// 开关

	AirMode mode;// 空调模式

	AirTemp temp;// 温度

	AirWindAmount wind_amount;// 风量

	AirWindDirection wind_direction;// 风向

	AirWindHoz wind_hoz; // 水平风向

	AirWindVer wind_ver; // 垂直风向

	// 其他状态

	AirSuper super_mode; // 强力

	AirSleep sleep;// 睡眠

	AirAidHot hot;// 辅热 / 干燥

	AirTime time;// 定时

	AirTempDisplay temp_display;// 温度显示

	AirPowerSaving power_saving;// 节能模式

	AirAnion anion;// 负离子

	AirComfort comfort;// 舒适

	AirFlashAir flash_air;// 清新、换气

	AirLight light;// 灯光

	AirWet wet;// 加湿

	AirMute mute;// 静音

	int time_limit; // 定时量，单位分钟(用于定时开，定时关) 30~1440，半小时~24小时

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public AirPower getPower() {
		return power;
	}

	public void setPower(AirPower power) {
		this.power = power;
	}

	public AirMute getMute() {
		return mute;
	}

	public void setMute(AirMute mute) {
		this.mute = mute;
	}

	public int getCaculate_number() {
		return caculate_number;
	}

	public void setCaculate_number(int caculate_number) {
		this.caculate_number = caculate_number;
	}

	public AirMode getMode() {
		return mode;
	}

	public void setMode(AirMode mode) {
		this.mode = mode;
	}

	public AirTemp getTemp() {
		return temp;
	}

	public void setTemp(AirTemp temp) {
		this.temp = temp;
	}

	public String getRemote_id() {
		return remote_id;
	}

	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
	}

	public AirTempDisplay getTemp_display() {
		return temp_display;
	}

	public void setTemp_display(AirTempDisplay temp_display) {
		this.temp_display = temp_display;
	}

	public AirWindAmount getWind_amount() {
		return wind_amount;
	}

	public void setWind_amount(AirWindAmount wind_amount) {
		this.wind_amount = wind_amount;
	}

	public AirWindDirection getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(AirWindDirection wind_direction) {
		this.wind_direction = wind_direction;
	}

	public AirWindHoz getWind_hoz() {
		return wind_hoz;
	}

	public void setWind_hoz(AirWindHoz wind_hoz) {
		this.wind_hoz = wind_hoz;
	}

	public AirWindVer getWind_ver() {
		return wind_ver;
	}

	public void setWind_ver(AirWindVer wind_ver) {
		this.wind_ver = wind_ver;
	}

	public AirTime getTime() // 空调的定时状态 默认为关，即上次定时后，关闭空调再开启时定时状态重置
	{
		if (time == null) {
			time = AirTime.TIME_OFF;
		}
		return time;
	}

	public void setTime(AirTime time) {
		this.time = time;
	}

	public AirSuper getSuper_mode() {
		return super_mode;
	}

	public void setSuper_mode(AirSuper super_mode) {
		this.super_mode = super_mode;
	}

	public AirSleep getSleep() {
		return sleep;
	}

	public void setSleep(AirSleep sleep) {
		this.sleep = sleep;
	}

	public AirAidHot getHot() {
		return hot;
	}

	public void setHot(AirAidHot hot) {
		this.hot = hot;
	}

	public AirPowerSaving getPower_saving() {
		return power_saving;
	}

	public void setPower_saving(AirPowerSaving power_saving) {
		this.power_saving = power_saving;
	}

	public AirAnion getAnion() {
		return anion;
	}

	public void setAnion(AirAnion anion) {
		this.anion = anion;
	}

	public AirComfort getComfort() {
		return comfort;
	}

	public void setComfort(AirComfort comfort) {
		this.comfort = comfort;
	}

	public AirFlashAir getFlash_air() {
		return flash_air;
	}

	public void setFlash_air(AirFlashAir flash_air) {
		this.flash_air = flash_air;
	}

	public AirLight getLight() {
		return light;
	}

	public void setLight(AirLight light) {
		this.light = light;
	}

	public AirWet getWet() {
		return wet;
	}

	public void setWet(AirWet wet) {
		this.wet = wet;
	}

	public int getCurrent_key() {
		return current_key;
	}

	public void setCurrent_key(int current_key) {
		this.current_key = current_key;
	}

	public int getLast_key() {
		return last_key;
	}

	public void setLast_key(int last_key) {
		this.last_key = last_key;
	}

	public int getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(int time_limit) {
		this.time_limit = time_limit;
	}

	
}

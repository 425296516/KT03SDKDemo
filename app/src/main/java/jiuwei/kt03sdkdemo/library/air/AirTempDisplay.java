/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“温度显示”状态 */
public enum AirTempDisplay
{
	DISPLAY_OUTDOOR_TEMP(0), DISPLAY_INDOOR_TEMP(1), DISPLAY_TARGET_TEMP(2), DISPLAY_NONE(3);

	private final int value;

	private AirTempDisplay(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirTempDisplay getTempDisplay(int value)
	{
		AirTempDisplay temp_display;
		switch (value)
		{
		case 0:
			temp_display = DISPLAY_OUTDOOR_TEMP;
			break;
		case 1:
			temp_display = DISPLAY_INDOOR_TEMP;
			break;

		case 2:
			temp_display = DISPLAY_TARGET_TEMP;
			break;
		case 3:
		default:
			temp_display = DISPLAY_NONE;
			break;
		}
		return temp_display;
	}
}

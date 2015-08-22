/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调"静音"开关状态 */
public enum AirMute
{
	MUTE_OFF(0), MUTE_ON(1);

	private final int value;

	private AirMute(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirMute getMuteState(int value)
	{
		AirMute mute;
		switch (value)
		{
		case 0:
			mute = MUTE_OFF;
			break;
		case 1:
			mute = MUTE_ON;
			break;
		default:
			mute = MUTE_OFF;
			break;
		}
		return mute;
	}
}

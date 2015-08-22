/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调“睡眠”开关状态 */
public enum AirSleep
{
	SLEEP_OFF(0), SLEEP_ON(1);

	private final int value;

	private AirSleep(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirSleep getSleepState(int value)
	{
		AirSleep sleep_state;
		switch (value)
		{
		case 0:
			sleep_state = SLEEP_OFF;
			break;
		case 1:
			sleep_state = SLEEP_ON;
			break;
		default:
			sleep_state = SLEEP_OFF;
			break;
		}
		return sleep_state;
	}
}

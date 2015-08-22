/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调"定时"开关状态 */
public enum AirTime
{
	TIME_OFF(0), TIME_ON(1);

	private final int value;

	private AirTime(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirTime getTimeState(int value)
	{
		AirTime time;
		switch (value)
		{
		case 0:
			time = TIME_OFF;
			break;
		case 1:
			time = TIME_ON;
			break;
		default:
			time = TIME_OFF;
			break;
		}
		return time;
	}
}

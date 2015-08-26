/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“舒适”开关状态 */
public enum AirComfort
{
	COMFORT_OFF(0), COMFORT_ON(1);

	private final int value;

	private AirComfort(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirComfort getComfortState(int value)
	{
		AirComfort comfort;
		switch (value)
		{
		case 0:
			comfort = COMFORT_OFF;
			break;
		case 1:
			comfort = COMFORT_ON;
			break;
		default:
			comfort = COMFORT_OFF;
			break;
		}
		return comfort;
	}
}

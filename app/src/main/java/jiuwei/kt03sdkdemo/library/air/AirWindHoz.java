/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调水平风向 开关状态 */
public enum AirWindHoz
{
	HOZ_OFF(0), HOZ_ON(1);

	private final int value;

	private AirWindHoz(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirWindHoz getWindHoz(int value)
	{
		AirWindHoz wind_hoz;
		switch (value)
		{
		case 0:
			wind_hoz = HOZ_OFF;
			break;
		case 1:
			wind_hoz = HOZ_ON;
			break;
		default:
			wind_hoz = HOZ_OFF;
			break;
		}
		return wind_hoz;
	}
}

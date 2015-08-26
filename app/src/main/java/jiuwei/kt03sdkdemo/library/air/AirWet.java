/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“加湿”开关状态 */
public enum AirWet
{
	WET_OFF(0), WET_ON(1);

	private final int value;

	private AirWet(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirWet getWetState(int value)
	{
		AirWet wet;
		switch (value)
		{
		case 0:
			wet = WET_OFF;
			break;
		case 1:
			wet = WET_ON;
			break;
		default:
			wet = WET_OFF;
			break;
		}
		return wet;
	}
}

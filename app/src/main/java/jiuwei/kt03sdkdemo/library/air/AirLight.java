/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“灯光”开关状态 */
public enum AirLight
{
	LIGHT_OFF(0), LIGHT_ON(1);

	private final int value;

	private AirLight(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirLight getLightState(int value)
	{
		AirLight light;
		switch (value)
		{
		case 0:
			light = LIGHT_OFF;
			break;
		case 1:
			light = LIGHT_ON;
			break;
		default:
			light = LIGHT_OFF;
			break;
		}
		return light;
	}
}

/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调开关状态枚举 */
public enum AirPower
{
	POWER_OFF(0), POWER_ON(1), WORK(2);

	private final int value;

	private AirPower(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirPower getPowerState(int value)
	{
		AirPower power;
		switch (value)
		{
		case 0:
			power = POWER_OFF;
			break;
		case 1:
			power = POWER_ON;
			break;
		case 2:
			power = WORK;
			break;
		default:
			power = POWER_OFF;
			break;
		}
		return power;
	}
}

/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调“节能”开关状态 */
public enum AirPowerSaving
{
	POWER_SAVING_OFF(0), POWER_SAVING_ON(1);

	private final int value;

	private AirPowerSaving(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirPowerSaving getPowerSavingState(int value)
	{
		AirPowerSaving anion;
		switch (value)
		{
		case 0:
			anion = POWER_SAVING_OFF;
			break;
		case 1:
			anion = POWER_SAVING_ON;
			break;
		default:
			anion = POWER_SAVING_OFF;
			break;
		}
		return anion;
	}
}

/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调“负离子”开关状态 */
public enum AirAnion
{
	ANION_OFF(0), ANION_ON(1);

	private final int value;

	private AirAnion(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirAnion getAnionState(int value)
	{
		AirAnion anion;
		switch (value)
		{
		case 0:
			anion = ANION_OFF;
			break;
		case 1:
			anion = ANION_ON;
			break;
		default:
			anion = ANION_OFF;
			break;
		}
		return anion;
	}
}

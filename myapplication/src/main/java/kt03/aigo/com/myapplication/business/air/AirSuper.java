/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调“超强”开关状态 */
public enum AirSuper
{
	SUPER_OFF(0), SUPER_ON(1);

	private final int value;

	private AirSuper(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirSuper getSuperState(int value)
	{
		AirSuper super_state;
		switch (value)
		{
		case 0:
			super_state = SUPER_OFF;
			break;
		case 1:
			super_state = SUPER_ON;
			break;
		default:
			super_state = SUPER_OFF;
			break;
		}
		return super_state;
	}
}

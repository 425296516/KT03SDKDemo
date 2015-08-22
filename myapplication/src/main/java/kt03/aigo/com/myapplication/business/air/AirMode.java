/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调“模式”枚举 */
public enum AirMode
{

	AUTO(0), WIND(1), DRY(2), HOT(3), COOL(4);

	private final int value;

	private AirMode(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirMode getMode(int value)
	{
		AirMode mode;
		switch (value)
		{
		case 0:
			mode = AUTO;
			break;
		case 1:
			mode = WIND;
			break;
		case 2:
			mode = DRY;
			break;

		case 3:
			mode = HOT;
			break;
		case 4:
			mode = COOL;
			break;
		default:
			mode = AUTO;
			break;
		}
		return mode;
	}
}

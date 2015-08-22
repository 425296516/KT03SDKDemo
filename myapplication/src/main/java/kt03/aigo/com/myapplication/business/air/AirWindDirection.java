/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调风向枚举 */
public enum AirWindDirection
{
	AUTO(0), UP(1), MIDDLE(2), DOWN(3);

	private final int value;

	private AirWindDirection(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirWindDirection getWindDirection(int value)
	{
		AirWindDirection direction;
		switch (value)
		{
		case 0:
			direction = AUTO;
			break;
		case 1:
			direction = UP;
			break;
		case 2:
			direction = MIDDLE;
			break;
		case 3:
			direction = DOWN;
			break;
		default:
			direction = AUTO;
			break;
		}
		return direction;
	}
}

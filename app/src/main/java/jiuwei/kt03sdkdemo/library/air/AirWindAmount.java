/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“风量”枚举 */
public enum AirWindAmount
{
	LEVEL_1(0), LEVEL_2(1), LEVEL_3(2), LEVEL_4(3), AUTO(4);

	private final int value;

	private AirWindAmount(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirWindAmount getWindAmount(int value)
	{
		AirWindAmount amount;
		switch (value)
		{
		case 0:
			amount = LEVEL_1;
			break;
		case 1:
			amount = LEVEL_2;
			break;

		case 2:
			amount = LEVEL_3;
			break;

		case 3:
			amount = LEVEL_4;
			break;

		case 4:
			amount = AUTO;
			break;

		default:
			amount = LEVEL_1;
			break;
		}
		return amount;
	}
}

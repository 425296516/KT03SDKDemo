/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调“辅热”状态 */
public enum AirAidHot
{
	AIDHOT_OFF(0), AIDHOT_ON(1);

	private final int value;

	private AirAidHot(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirAidHot getAidHottate(int value)
	{
		AirAidHot hot;
		switch (value)
		{
		case 0:
			hot = AIDHOT_OFF;
			break;
		case 1:
			hot = AIDHOT_ON;
			break;
		default:
			hot = AIDHOT_OFF;
			break;
		}
		return hot;
	}
}

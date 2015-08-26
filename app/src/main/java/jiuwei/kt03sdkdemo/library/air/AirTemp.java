/**
 * 
 */
package jiuwei.kt03sdkdemo.library.air;

/** 空调温度枚举 */
public enum AirTemp
{
	T16(16), T17(17), T18(18), T19(19), T20(20), T21(21), T22(22), T23(23), T24(24), T25(25), T26(26), T27(27), T28(28), T29(29), T30(30), T31(31);

	private final int value;

	private AirTemp(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirTemp getAirTemp(int value)
	{
		AirTemp temp;
		switch (value)
		{
		case 16:
			temp = T16;
			break;
		case 17:
			temp = T17;
			break;
		case 18:
			temp = T18;
			break;
		case 19:
			temp = T19;
			break;
		case 20:
			temp = T20;
			break;
		case 21:
			temp = T21;
			break;
		case 22:
			temp = T22;
			break;
		case 23:
			temp = T23;
			break;
		case 24:
			temp = T24;
			break;
		case 25:
			temp = T25;
			break;
		case 26:
			temp = T26;
			break;
		case 27:
			temp = T27;
			break;
		case 28:
			temp = T28;
			break;
		case 29:
			temp = T29;
			break;
		case 30:
			temp = T30;
			break;
		case 31:
			temp = T31;
			break;
		default:
			temp = T17;
			break;
		}
		return temp;
	}
}

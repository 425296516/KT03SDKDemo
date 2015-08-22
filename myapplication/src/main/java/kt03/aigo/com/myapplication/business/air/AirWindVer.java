/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调垂直风向开关状态 */
public enum AirWindVer
{
	VER_OFF(0), VER_ON(1);

	private final int value;

	private AirWindVer(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirWindVer getWindVer(int value)
	{
		AirWindVer wind_ver;
		switch (value)
		{
		case 0:
			wind_ver = VER_OFF;
			break;
		case 1:
			wind_ver = VER_ON;
			break;
		default:
			wind_ver = VER_OFF;
			break;
		}
		return wind_ver;
	}
}

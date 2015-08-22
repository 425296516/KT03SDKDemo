/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;

/** 空调"换气"开关状态 */
public enum AirFlashAir
{
	FLASH_OFF(0), FLASH_ON(1);

	private final int value;

	private AirFlashAir(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return this.value;
	}

	public static AirFlashAir getFlashAirState(int value)
	{
		AirFlashAir flash_state;
		switch (value)
		{
		case 0:
			flash_state = FLASH_OFF;
			break;
		case 1:
			flash_state = FLASH_ON;
			break;
		default:
			flash_state = FLASH_OFF;
			break;
		}
		return flash_state;
	}
}

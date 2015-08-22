/**
 * 
 */
package kt03.aigo.com.myapplication.business.air;


import kt03.aigo.com.myapplication.R;

/**
 * @author jiangs
 */

public class AirRemoteStateDisplay {
	AirRemoteStateDisplay() {
	}
	

	

	public static int getModeStrId(AirRemoteState airState) {
		if (airState.mode == AirMode.AUTO) {
			return R.string.auto;
		} else if (airState.mode == AirMode.COOL) {
			return R.string.cool;
		} else if (airState.mode == AirMode.HOT) {
			return R.string.heart;
		} else if (airState.mode == AirMode.DRY) {
			return R.string.dry;
		} else if (airState.mode == AirMode.WIND) {
			return R.string.wind;
		}
		return 0;

	}

	public static int getWindStrId(AirRemoteState airState) {
		if (airState.wind_amount == AirWindAmount.AUTO) {
			return R.string.wind_auto;
		} else if (airState.wind_amount == AirWindAmount.LEVEL_1) {
			return R.string.wind_1;
		} else if (airState.wind_amount == AirWindAmount.LEVEL_2) {
			return R.string.wind_2;
		} else if (airState.wind_amount == AirWindAmount.LEVEL_3) {
			return R.string.wind_3;
		} else if (airState.wind_amount == AirWindAmount.LEVEL_4) {
			return R.string.wind_4;
		}
		return 0;

	}
	
	public static int getTempStr(AirRemoteState airState) {
		if (airState.temp == AirTemp.T16) {
			return 16;
		} else if (airState.temp == AirTemp.T17) {
			return 17;
		} else if (airState.temp == AirTemp.T18) {
			return 18;
		} else if (airState.temp == AirTemp.T19) {
			return 19;
		} else if (airState.temp == AirTemp.T20) {
			return 20;
		}else if (airState.temp == AirTemp.T21) {
			return 21;
		}
		else if (airState.temp == AirTemp.T22) {
			return 22;
		}
		else if (airState.temp == AirTemp.T23) {
			return 23;
		}
		else if (airState.temp == AirTemp.T24) {
			return 24;
		}
		else if (airState.temp == AirTemp.T25) {
			return 25;
		}
		else if (airState.temp == AirTemp.T26) {
			return 26;
		}
		else if (airState.temp == AirTemp.T27) {
			return 27;
		}
		else if (airState.temp == AirTemp.T28) {
			return 28;
		}
		else if (airState.temp == AirTemp.T29) {
			return 29;
		}
		else if (airState.temp == AirTemp.T30) {
			return 30;
		}
		
		
		return 0;

	}
}

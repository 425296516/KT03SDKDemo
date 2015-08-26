package kt03.aigo.com.myapplication.business.util;

import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Remote;

public class RemoteUtils {
    private final static String TAG = "RemoteUtils";

    /**
     * 是否是态的空调遥控器
     */
    public static boolean isMultiAirRemote(Remote remote) {
        // 缺乏数据
        if (remote == null || remote.getKeys() == null || remote.getKeys().size() == 0) {
            return false;
        }
        // 不是空调类型
        if (remote.getType() != Globals.AIR_CONDITIONER) {
            return false;
        }

        // 判断是否是协议空调
        for (IRKey key : remote.getKeys()) {
            if (key != null && key.getProtocol() > 0) {
                return true;
            }
        }

        return false;
    }


    public static boolean isDiyAirRemote(Remote remote) {
        if (remote.getType() != Globals.AIR_CONDITIONER) {
            return false;
        }
        // 判断是否是DIY的组合状态空调
        if (remote.getAir_keys() != null) {
            return true;
        }

        return false;
    }



    public static String getAirStrFrom(AirRemoteState state) {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if (state.getPower() == AirPower.POWER_OFF) {
            return "POWEROFF";
        } else {
            switch (state.getMode()) {
//            case MODE_AUTO:
//                ac_text_mode.setText("自动");
//                break;
                case COOL:

                    code2 = "C";
                    break;

                case HOT:

                    code2 = "H";
                    break;
                default:
                    code2 = "C";
                    break;
            }
            switch (state.getWind_amount()) {

                case LEVEL_1:

                    code4 = "F1";
                    break;
                case LEVEL_2:

                    code4 = "F2";
                    break;
                case LEVEL_3:

                    code4 = "F3";
                    break;
                default:
                    code4 = "F1";
                    break;
            }
            switch (state.getTemp()) {
                case T16:

                    code3 = "16";
                    break;
                case T17:

                    code3 = "17";
                    break;
                case T18:

                    code3 = "18";
                    break;
                case T19:

                    code3 = "19";
                    break;
                case T20:

                    code3 = "20";
                    break;
                case T21:

                    code3 = "21";
                    break;
                case T22:

                    code3 = "22";
                    break;
                case T23:

                    code3 = "23";
                    break;
                case T24:

                    code3 = "24";
                    break;
                case T25:

                    code3 = "25";
                    break;
                case T26:

                    code3 = "26";
                    break;
                case T27:

                    code3 = "27";
                    break;
                case T28:

                    code3 = "28";
                    break;
                case T29:

                    code3 = "29";
                    break;
                case T30:

                    code3 = "30";
                    break;
                default:
                    code3 = "24";
                    break;
            }
            switch (state.getWind_direction()) {
                case AUTO:

                    code1 = "S";
                    break;
                case DOWN:
                case MIDDLE:
                case UP:
                    code1 = "W1";
//               
                    break;
                default:
                    code1 = "S";
                    break;
            }
            return code1 + code2 + code3 + code4;
        }
    }
}

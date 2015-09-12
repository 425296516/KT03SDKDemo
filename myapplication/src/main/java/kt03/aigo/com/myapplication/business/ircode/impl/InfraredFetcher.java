/**
 *
 */
package kt03.aigo.com.myapplication.business.ircode.impl;

import android.content.Context;
import android.util.Log;

import com.etek.ircore.RemoteCore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.KeyType;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.IInfraredFetcher;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;

/**
 * 信号获取类
 *
 * @author Administrator
 */
public class InfraredFetcher implements IInfraredFetcher {
    private final String TAG = InfraredFetcher.class.getCanonicalName();

    private Context mContext;

    public InfraredFetcher(Context context) {
        this.mContext = context;
    }

    ;

    /**
     * 获取空调按键的信号并调整空调状态
     */
    public List<Infrared> fetchAirInfrareds(Remote remote, IRKey key,
                                            AirRemoteState air_state) {
        if (remote == null || key == null || air_state == null) {
            Log.d(TAG, "fetchAirInfrareds.........参数空....remote==null||key==null||air_state==null");
            return null;
        }
        Log.d(TAG, "fetchAirInfrareds........................................key.getProtocol=" + key.getProtocol());

        if (key.getProtocol() > 0) // 协议空调按键
        {
            AirRemoteStateMananger.getInstance(mContext).fitAirState(air_state, key);
            Log.d(TAG, "fetchAirInfrareds..............................协议空调按键..........air_state = " + air_state);
            return fetchProtocolAirInfrareds(key, air_state);
        } else
        // DIY的组合空调按键
        {
            Log.d(TAG, "fetchAirInfrareds........................DIY的组合空调按键................air_state = " + air_state);

            AirRemoteStateMananger.getInstance(mContext).fitAirState(air_state,  key);
            return fetchDiyAirInfrareds(remote, key, air_state);
        }
    }

    /**
     * 获取本地云"协议"空调按键的信号并调整空调状态
     */
    private List<Infrared> fetchProtocolAirInfrareds(IRKey key,
                                                     AirRemoteState air_state) {
        Log.d(TAG, "fetchProtocolAirInfrareds........................................协议空调码获取");

        if (air_state != null) {
            if (air_state.getPower() == AirPower.POWER_OFF
                    && key.getType() != KeyType.POWER) {
                Log.e(TAG,
                        "fetchProtocolAirInfrareds.............关键状态点击其他按键 不作响应");
                return null;
            }

            Log.d(TAG, "fetchProtocolAirInfrareds............计算信号");

            return loadAirCodes(air_state);
        }
        return null;
    }

    /**
     * 获取DIY的组合空调按键的信号
     */
    private List<Infrared> fetchDiyAirInfrareds(Remote remote, IRKey key,
                                                AirRemoteState air_state) {
        Log.d(TAG, "fetchDiyAirInfrareds....##############....DIY空调码获取....air_state = "
                + new Gson().toJson(air_state));

        if (remote == null || remote.getKeys() == null || key == null
                || air_state == null) {
            Log.e(TAG, "fetchDiyAirInfrareds........DIY空调码获取..！！！！.数据不完整");

            return null;
        }
        if (remote.getAir_keys() == null) {
            Log.e(TAG, "fetchDiyAirInfrareds........DIY空调码码库 ERROR");
            return null;
        }

        if (air_state.getPower() == AirPower.POWER_OFF
                && key.getType() != KeyType.POWER) {
            return null;
        }

        if (key.getType() != KeyType.POWER) // 如果按钮点击的不是电源键，记录最后按键类型
        {
            air_state.setLast_key(key.getType());
        }

        List<Infrared> infrareds = null;

        Log.d(TAG, "fetchDiyAirInfrareds........DIY空调码获取.....air_state.mode = "
                + air_state.getMode());

        if (key.getType() == KeyType.POWER || key.getType() == KeyType.MODE
                || key.getType() == KeyType.TEMP_UP
                || key.getType() == KeyType.TEMP_DOWN
                || key.getType() == KeyType.WIND_AMOUNT
                || key.getType() == KeyType.WIND_HORIZONTAL) {
            String airIndex = RemoteUtils.getAirStrFrom(air_state);

            infrareds = getRemoteAirKeyDB(remote, airIndex);

        }

        return infrareds;
    }

    List<Infrared> getRemoteAirKeyDB(Remote remote, String value) {
        for (IRKey key : remote.getAir_keys()) {

            if (key.getName().equalsIgnoreCase(value)) {
                Log.d(TAG,"value="+value);
                return key.getInfrareds();
            }
        }
        return null;

    }


    /**
     * 获取协议空调的当前状态信号对象
     */
    public List<Infrared> loadAirCodes(AirRemoteState air_state) {
        if (air_state == null) {
            return null;
        }
        List<Infrared> infrareds = new ArrayList<Infrared>();

        int[] state = new int[19];
        /**
         * AirPower power;// 开关 AirMode mode;// 空调模式 AirTemp temp;// 温度
         * AirWindAmount wind_amount;// 风量 AirWindDirection wind_direction;// 风向
         * AirWindHoz wind_hoz; // 水平风向 AirWindVer wind_ver; // 垂直风向
         *
         * // 其他状态 AirSuper super_mode; // 强力 AirSleep sleep;// 睡眠 AirAidHot
         * hot;// 辅热 AirTime time;// 定时 AirTempDisplay temp_display;//温度显示
         *
         * AirPowerSaving power_saving;// 节能模式 AirAnion anion;// 负离子 AirComfort
         * comfort;// 舒适 AirFlashAir flash_air;// 清新、换气 AirLight light;// 灯光
         * AirWet wet;// 加湿 AirMute mute;//静音
         */

        state[0] = (int) air_state.getProtocol();
        state[1] = (int) air_state.getPower().value();
        state[2] = (int) air_state.getMode().value();
        state[4] = (int) air_state.getTemp().value();
        state[3] = (int) air_state.getWind_amount().value();
        if (air_state.getWind_direction() != null) {
            state[5] = (byte) air_state.getWind_direction().value();
            state[6] = 0;

        } else {
            state[5] = 0;
            state[6] = (byte) air_state.getWind_hoz().value();

        }
        // state[6] = (byte) air_state.getWind_ver().value();
        state[7] = getAirKey(air_state.getCurrent_key());

        // state[7] = (byte) air_state.getSuper_mode().value();
        state[8] = (byte) air_state.getSleep().value();
        state[9] = (byte) air_state.getHot().value();
        state[10] = (byte) air_state.getTime().value();
        state[11] = (byte) air_state.getTemp_display().value();
        state[12] = (byte) air_state.getPower_saving().value();
        state[13] = (byte) air_state.getAnion().value();
        state[14] = (byte) air_state.getComfort().value();
        state[15] = (byte) air_state.getFlash_air().value();
        state[16] = (byte) air_state.getLight().value();
        state[17] = (byte) air_state.getWet().value();
        state[18] = (byte) air_state.getMute().value();

        byte[] airCode = RemoteCore.getProntoAirData(state);
        IRCode ir = RemoteCore.ETcodeToPronto(airCode);
        Infrared inf = new Infrared();
        inf.setInfrared(ir);
        infrareds.add(inf);
        // Infrared ir =
        // LocalIrDb.getIrDb(mContext).getAirCode(air_state.getProtocol(),
        // air_state.getCurrent_key(), air_state.getCaculate_number(),
        // air_state.getTime_limit(), state);
        // infrareds.add(ir);
        // if (air_state.getCurrent_key() == KeyType.POWER &&
        // air_state.getPower() == AirPower.POWER_ON && air_state.getLast_key()
        // >0) // 如果当前键是开机键，则需要生成上次关机前的信号
        // {
        // air_state.setCurrent_key(air_state.getLast_key());
        // Log.v(TAG, "loadAirCode.....############..生成上次关机前的信号.....last_key = "
        // + air_state.getLast_key() + ",current_key = " +
        // air_state.getCurrent_key() + ",caculate_number = " +
        // air_state.getCaculate_number() + ",cime_limit = " +
        // air_state.getTime_limit());
        // air_state.setCaculate_number(air_state.getCaculate_number() + 1);
        // Infrared last_ir =
        // LocalIrDb.getIrDb(mContext).getAirCode(air_state.getProtocol(),
        // air_state.getCurrent_key(), air_state.getCaculate_number(),
        // air_state.getTime_limit(), state);
        // infrareds.add(last_ir);
        // }

        state = null;
        Log.d(TAG, "loadAirCode.....############.....获取到的信号有 ——> "
                + new Gson().toJson(infrareds));
        return infrareds;
    }

    int getAirKey(int key) {
        int k = 0;
        switch (key) {
            case KeyType.POWER:
                k = 0;
                break;
            case KeyType.MODE:
                k = 1;
                break;
            case KeyType.TEMP_UP:
                k = 3;
                break;
            case KeyType.TEMP_DOWN:
                k = 4;
                break;
            case KeyType.WIND_AMOUNT:
                k = 2;
                break;
            case KeyType.WIND_HORIZONTAL:
                k = 5;
                break;
            case KeyType.WIND_VERTICAL:
                k = 6;
                break;
            default:
                k = 7;
                break;
        }
        return k;
    }
}

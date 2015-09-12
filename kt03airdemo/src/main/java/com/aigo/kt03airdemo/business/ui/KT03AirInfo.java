package com.aigo.kt03airdemo.business.ui;

import com.aigo.kt03airdemo.business.kt03.KT03AirIndexObject;

/**
 * Created by zhangcirui on 15/9/10.
 */
public class KT03AirInfo {

    private KT03AirIndexObject airinfo;

    public KT03AirIndexObject getAirinfo() {
        return airinfo;
    }

    public void setAirinfo(KT03AirIndexObject airinfo) {
        this.airinfo = airinfo;
    }

    @Override
    public String toString() {
        return "KT03AirInfo{" +
                "airinfo=" + airinfo +
                '}';
    }
}

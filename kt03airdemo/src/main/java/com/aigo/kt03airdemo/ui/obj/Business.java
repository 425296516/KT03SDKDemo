package com.aigo.kt03airdemo.ui.obj;


import com.aigo.kt03airdemo.ui.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinqi on 15/7/25.
 */
public class Business {

    public List<DeviceInfoObject> getDeviceInfoObjectList(){
        List<DeviceInfoObject> list = new ArrayList<>();

        DeviceInfoObject d1 = new DeviceInfoObject();
        d1.setId("1");
        d1.setDeviceType(Constant.APPLIANCE_CONTROL_AIR_CONDITIONER);
        d1.setDeviceName("我的空调");
        d1.setStatus(true);


        DeviceInfoObject d2 = new DeviceInfoObject();
        d2.setId("2");
        d2.setDeviceType(Constant.APPLIANCE_CONTROL_PURIFIER);
        d2.setDeviceName("我的净化器");
        d2.setStatus(false);



        DeviceInfoObject d3 = new DeviceInfoObject();
        d3.setId("3");
        d3.setDeviceType(Constant.APPLIANCE_CONTROL_HUMIDIFIER);
        d3.setDeviceName("我的加湿器");
        d3.setStatus(true);


        DeviceInfoObject d4 = new DeviceInfoObject();
        d4.setId("4");
        d4.setDeviceType(Constant.APPLIANCE_CONTROL_AIR_CONDITIONER);
        d4.setDeviceName("我的空调");
        d4.setStatus(false);

        DeviceInfoObject d5 = new DeviceInfoObject();
        d5.setId("5");
        d5.setDeviceType(Constant.APPLIANCE_CONTROL_HUMIDIFIER);
        d5.setDeviceName("我的加湿器");
        d5.setStatus(true);


        DeviceInfoObject d6 = new DeviceInfoObject();
        d6.setId("6");
        d6.setDeviceType(Constant.APPLIANCE_CONTROL_AIR_CONDITIONER);
        d6.setDeviceName("我的空调");
        d6.setStatus(false);


        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
        list.add(d6);

        return list;

    }

}

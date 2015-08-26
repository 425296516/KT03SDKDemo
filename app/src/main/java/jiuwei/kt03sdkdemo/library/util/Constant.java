package jiuwei.kt03sdkdemo.library.util;


import java.util.List;

import jiuwei.kt03sdkdemo.library.bean.Infrared;

/**
 * Created by zhangcirui on 15/8/18.
 */
public class Constant {

    public static String GETSERVERBRAND = "http://222.191.229.234:10068/SmartHomeServer/wyf/getBrand/";
    public static String GETSERVERKEYWOARDS = "http://222.191.229.234:10068/SmartHomeServer/wyf/getModelNumberByKeywords/";//获取关键词搜索遥控器型号的接口
    public static String GETSERVERREMOTEKEY = "http://222.191.229.234:10068/SmartHomeServer/wyf/getRemoteKey/";//根据遥控器型号获取键码的接口
    public static String GETSERVERSEARCHREMOTE = "http://222.191.229.234:10068/SmartHomeServer/wyf/getSearchRemoteKey/";//获取某一类型某一品牌的键码接口

    public static final long TIME_OUT = 1000 * 20;
    public static final String CODE = "UTF-8";

    public static List<Infrared> infraredList;


}

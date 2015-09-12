package com.aigo.kt03airdemo.ui.util;


import com.aigo.kt03airdemo.ui.obj.GasDetailObj;
import com.aigo.kt03airdemo.ui.obj.IndexLevelObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinqi on 15/9/3.
 */
public class UiUtil {

    public static GasDetailObj getGasDetail(String gas, String level) {
        GasDetailObj gasDetailObj = new GasDetailObj();
        List<IndexLevelObj> list = getIndexLevelList(gas,level);

        switch (gas) {
            case Constant.GAS_JIAQUAN:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("甲醛主要来自人造板材、家具和装修中使用的黏合 剂以及地毯等合成织物,   浓度超标会引起恶心、呕 吐、咳嗽、胸闷、气喘甚至肺气");
                gasDetailObj.setHealthTips("如果您闻到异味，说明家中的甲醛已经达到影响您的浓度，请及时开窗通风、打开空气净化器，如果长时间暴露在当前环境下会对身体造成重要");

                break;
            case Constant.GAS_SHIDU:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("地理，气候，季节等环境因素及长期开空调，加湿器，洒水拖地等行为影响。");
                gasDetailObj.setHealthTips("室内干湿度适宜，适合休息。");

                break;
            case Constant.GAS_WENDU:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("地理，气候，季节等环境因素及长期开空调，加湿器等行为影响。");
                gasDetailObj.setHealthTips("室内炎热，请打开空调调整室内温度。");

                break;
            case Constant.GAS_ZAOYIN:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("目前的大中城市中，主要有以下噪声源：交通运输噪声、工业机械噪声（室内噪声污染的主要来源）、城市建筑噪声、社会生活和公共场所噪声");
                gasDetailObj.setHealthTips("当前环境正常，适合工作。");

                break;
            case Constant.GAS_CO2:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("主要来源有三个：一是动植物的呼吸作用；二是化石燃料的燃烧；三是微生物的分解作用。");
                gasDetailObj.setHealthTips("CO2含量符合标准，室内环境一般。");

                break;
            case Constant.GAS_PM25:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("自然来源：风扬尘土、火山灰、森林火灾、漂浮的海盐、花粉、真菌孢子、细菌。人为来源包括：道路扬尘、建筑施工扬尘、工业粉尘、厨房烟");
                gasDetailObj.setHealthTips("当前室内PM2.5含量已经处于污染边缘，会对老人与小孩造成影响，请及时开窗或打开空气净化器，保持室内空气清新。");

                break;
            case Constant.GAS_VOC:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("室内主要来自燃煤和天然气等燃烧产物、吸烟、采暖和烹调等的烟雾，建筑和装饰材料，家具，家用电器，清洁剂和人体本身的排放等。");
                gasDetailObj.setHealthTips("您家中的TVOC含量极低，属于正常范围，对人体健康基本无影响。");

                break;
            default:
                break;

        }


        return gasDetailObj;
    }


    private static List<IndexLevelObj> getIndexLevelList(String gas, String level) {
        List<IndexLevelObj> list = new ArrayList<>();
        IndexLevelObj indexLevelObj1 = new IndexLevelObj();
        IndexLevelObj indexLevelObj2 = new IndexLevelObj();
        IndexLevelObj indexLevelObj3 = new IndexLevelObj();
        IndexLevelObj indexLevelObj4 = new IndexLevelObj();
        IndexLevelObj indexLevelObj5 = new IndexLevelObj();

        switch (gas) {
            case Constant.GAS_JIAQUAN:
                indexLevelObj1.setTag("清新");
                indexLevelObj1.setDescription("对人体无太大影响，能够正常生活");
                indexLevelObj1.setValue("0mg/m³-0.025mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("较清新");
                indexLevelObj2.setDescription("对正常人无太大影响，儿童、老人及呼 吸疾病患者会发生轻微气喘。");
                indexLevelObj2.setValue("0.025-0.04mg/m³");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("浑浊");
                indexLevelObj3.setDescription("有异味，正常人会感到刺激眼睛、头晕 等不适");
                indexLevelObj3.setValue("0.04-0.125mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("很浑浊");
                indexLevelObj4.setDescription("刺激眼睛引起流泪，会出现恶心胸闷等 症状");
                indexLevelObj4.setValue("0.125-0.275mg/m³");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("窒息");
                indexLevelObj5.setDescription("可引起恶心呕吐，咳嗽胸闷，气喘甚至 肺水肿");
                indexLevelObj5.setValue("0.275mg/m³以上");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 0 && value < 25) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if (value >= 25 && value < 40) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 40 && value < 125) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 125 && value < 275) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 275) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("清新")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("较清新")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("浑浊")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("很浑浊")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("窒息")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_SHIDU:
                indexLevelObj1.setTag("适宜");
                indexLevelObj1.setDescription("室内干湿度适宜，适合休息");
                indexLevelObj1.setValue("50-60%");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("较适宜");
                indexLevelObj2.setDescription("室内湿度符合范围，体感可以接受");
                indexLevelObj2.setValue("55-65%/45-50%");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("较潮湿");
                indexLevelObj3.setDescription("室内湿度湿润");
                indexLevelObj3.setValue("65-70%");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("潮湿");
                indexLevelObj4.setDescription("室内较湿润，请打开空调进行调节");
                indexLevelObj4.setValue("70-75%");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("很潮湿");
                indexLevelObj5.setDescription("室内过于湿润，高湿度的环境会同样会 导致中暑，请您立刻打开空调");
                indexLevelObj5.setValue("75-100%");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 50 && value < 60) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if ((value >= 60 && value < 65) || (value >= 45 && value < 50)) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 60 && value < 70) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 70 && value < 75) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 75) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("适宜")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("较适宜")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("较潮湿")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("潮湿")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("很潮湿")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_WENDU:
                indexLevelObj1.setTag("舒适");
                indexLevelObj1.setDescription("室内温度舒适");
                indexLevelObj1.setValue("22-26℃");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("较舒适");
                indexLevelObj2.setDescription("室内温度可以接受");
                indexLevelObj2.setValue("27-29℃/20-22℃");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("微热");
                indexLevelObj3.setDescription("温度较热可以打开电扇或用凉水擦地");
                indexLevelObj3.setValue("29-32℃");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("炎热");
                indexLevelObj4.setDescription("室内炎热，请打开空调调整室内温度");
                indexLevelObj4.setValue("32-35℃");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("高温");
                indexLevelObj5.setDescription("室温过高，在温度过高的环境下容易导 致中暑，请立刻打开空调");
                indexLevelObj5.setValue(">36℃");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 22 && value < 26) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if ((value >= 26 && value < 29) || (value >= 20 && value < 22)) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 29 && value < 32) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 32 && value < 35) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 35) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("舒适")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("较舒适")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("微热")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("炎热")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("高温")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_ZAOYIN:
                indexLevelObj1.setTag("安静");
                indexLevelObj1.setDescription("轻声细语，适合休息");
                indexLevelObj1.setValue("0-45分贝/0-30分贝");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("舒适");
                indexLevelObj2.setDescription("白天正常生活");
                indexLevelObj2.setValue("45-50分贝/30-45分贝");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("喧闹");
                indexLevelObj3.setDescription("正常生活，对正常人无较大影响，孕妇、 儿童会有不舒服");
                indexLevelObj3.setValue("50-55分贝/45-50分贝");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("吵闹");
                indexLevelObj4.setDescription("长期暴露在60-75分贝中，妨碍睡眠，焦 虑、精神紧张和内分泌系统失调。");
                indexLevelObj4.setValue("56-65分贝/50-60分贝");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("刺耳");
                indexLevelObj5.setDescription("耳朵发痒、疼痛，导致头晕、头痛尤其 导致孕妇胎儿发育畸形甚至流产。");
                indexLevelObj5.setValue("65分贝以上/60分贝以上");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 22 && value < 26) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if ((value >= 26 && value < 29) || (value >= 20 && value < 22)) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 29 && value < 32) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 32 && value < 35) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 35) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("安静")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("舒适")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("喧闹")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("吵闹")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("刺耳")){
                    indexLevelObj5.setIsMeetStandard(true);
                }


                break;
            case Constant.GAS_CO2:
                indexLevelObj1.setTag("清新");
                indexLevelObj1.setDescription("空气清新，呼吸顺畅");
                indexLevelObj1.setValue("0-350ppm");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("较清新");
                indexLevelObj2.setDescription("一般环境");
                indexLevelObj2.setValue("351-600ppm");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("浑浊");
                indexLevelObj3.setDescription("感觉空气浑浊，并开始觉得昏昏欲睡");
                indexLevelObj3.setValue("600-1000ppm");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("很浑浊");
                indexLevelObj4.setDescription("感觉头痛、嗜睡、呆滞、注意力无法集 中、心跳加速、轻度恶心");
                indexLevelObj4.setValue("1000～1500ppm");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("窒息");
                indexLevelObj5.setDescription("可能导致严重缺氧，造成永久性脑损伤、 昏迷、甚至死亡");
                indexLevelObj5.setValue(">1500ppm");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 0 && value < 350) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if (value >= 350 && value < 600) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 600 && value < 1000) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 1000 && value < 1500) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 1500) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("清新")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("较清新")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("浑浊")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("很浑浊")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("窒息")){
                    indexLevelObj5.setIsMeetStandard(true);
                }


                break;
            case Constant.GAS_PM25:
                indexLevelObj1.setTag("清新");
                indexLevelObj1.setDescription("含量极低，室内空气洁净，对人体无害");
                indexLevelObj1.setValue("0mg/m³-0.025mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("较清新");
                indexLevelObj2.setDescription("对人体健康基本无影响");
                indexLevelObj2.setValue("0.025-0.04mg/m3");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("浑浊");
                indexLevelObj3.setDescription("正常范围，正常人无太大影响，儿童、 老人会有些许不适");
                indexLevelObj3.setValue("0.076mg/m³-0.15mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("很浑浊");
                indexLevelObj4.setDescription("长时间会感到眼部、呼吸系统发炎，造 成咳嗽鼻炎、加重哮喘导致支气管炎等");
                indexLevelObj4.setValue("0.15mg/m³-0.35mg/m³");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("窒息");
                indexLevelObj5.setDescription("当人长时间生活在该浓度时，会增加致 癌几率，同时心脏疾病几率增加");
                indexLevelObj5.setValue(">0.35mg/m³");
                indexLevelObj5.setIsMeetStandard(false);

//                if (value >= 0 && value < 25) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if (value >= 25 && value < 40) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 40 && value < 150) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 150 && value < 350) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                } else if (value >= 350) {
//                    indexLevelObj5.setIsMeetStandard(true);
//                }

                if(level.equals("清新")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("较清新")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("浑浊")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("很浑浊")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("窒息")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_VOC:
                indexLevelObj1.setTag("清新");
                indexLevelObj1.setDescription("含量低，室内空气洁净，对人体无害");
                indexLevelObj1.setValue("0mg/m³-0.3mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("浑浊");
                indexLevelObj2.setDescription("临界值，正常人无太大影响，儿童、老 人会有些许不适。");
                indexLevelObj2.setValue("0.3mg/m³-0.4mg/m³");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("很浑浊");
                indexLevelObj3.setDescription("产生刺激和不适，与其他因素联合作用 时，可能出现头晕、头痛、胸闷等症状");
                indexLevelObj3.setValue("0.4mg/m³-0.8mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("窒息");
                indexLevelObj4.setDescription("长时间会感到眼部、呼吸系统发炎，造 成咳嗽鼻炎、加重哮喘导致支气管炎等");
                indexLevelObj4.setValue(">0.8mg/m³");
                indexLevelObj4.setIsMeetStandard(false);

//                if (value >= 0 && value < 3) {
//                    indexLevelObj1.setIsMeetStandard(true);
//                } else if (value >= 3 && value < 4) {
//                    indexLevelObj2.setIsMeetStandard(true);
//                } else if (value >= 4 && value < 8) {
//                    indexLevelObj3.setIsMeetStandard(true);
//                } else if (value >= 8) {
//                    indexLevelObj4.setIsMeetStandard(true);
//                }

                if(level.equals("清新")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("浑浊")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("很浑浊")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("窒息")){
                    indexLevelObj4.setIsMeetStandard(true);
                }

                break;
            default:
                break;
        }

        list.add(indexLevelObj1);
        list.add(indexLevelObj2);
        list.add(indexLevelObj3);
        list.add(indexLevelObj4);

        if(gas != Constant.GAS_VOC && indexLevelObj5.getTag()!=null){
            list.add(indexLevelObj5);
        }

        return list;
    }
}

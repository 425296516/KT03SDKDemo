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
                String source = new StringBuffer()
                        .append("1、来自于室内装饰时使用的胶合板、细木工板、中密度纤维板和刨花板等人造板材；")
                        .append("\n2、来自于室内家具，其中包括人造板家具、布艺家具、厨房家具等;")
                        .append("\n3、来自于含有甲醛成分的其他各类装饰材料，特别是不合格的白乳胶和涂料等;")
                        .append("\n4、平自于室内装饰纺织品，其中包括床上用品、墙布、墙纸、地毯与窗帘等。").toString();

                gasDetailObj.setList(list);
                gasDetailObj.setSource(source);

                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("您家中的甲醛含量极低，放心休息哦！");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前屋内甲醛含量值得引起注意，已经对儿童或老人产生影响，请及时开窗通风、打开空气净化器");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("如果您闻到异味，说明家中的甲醛已经达到能够影响您的浓度，请及时开窗通风、打开空气净化器，如果长时间暴露在当前环境下则会对身体造成重要影响。");
                }else if(list.get(3).isMeetStandard()){
                    gasDetailObj.setHealthTips("家中甲醛指标已经超标，请您马上打开空气净化器、开窗通风，有条件也可以购买甲醛宝等除甲醛产品，改善家中空气环境。");
                }else if(list.get(4).isMeetStandard()){
                    gasDetailObj.setHealthTips("您家中的甲醛含量已经爆表了！长时间暴露可引起慢性呼吸道疾病，鼻咽癌、结肠癌，妊娠综合症、引起新生儿染色体异常、白血病，引起青少年记忆力和智力下降等各种问题。请您立刻打开空气净化器、开窗通风，并到室外透透气，室内空气达标后再回到室内，如果有条件可以找专业的甲醛清除工作室。");
                }

                break;
            case Constant.GAS_SHIDU:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("地理，气候，季节等环境因素及长期开空调，加湿器，洒水拖地等行为影响");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内过于干燥，易导致上火、内分泌失调等，可用凉水拖地，或打开加湿器。");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内湿度符合范围，既不会觉得太干，又不会觉得太潮湿，体感可以接受。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内体感认为潮湿，高湿度的环境影响健康，建议适量调节室内湿度。");
                }

                break;
            case Constant.GAS_WENDU:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("地理，气候，季节等环境因素及长期开空调，加湿器等行为影响");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内温度偏低，易着凉导致感冒，请注意保温");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内温度符合指标要求。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内温度偏高，请适量调整温度。");
                }

                break;
            case Constant.GAS_ZAOYIN:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("在日常生活中，诸如室内各项家庭用具均会发生声音，如冷气机、音响、抽油烟机、电视、空调设备，均为噪音源，另也会受到室外如急驰而过的车辆、鸣笛、人们的喧闹及各式各样的声音影响。");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前非常安静，十分适合休息。");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前比较安静，适合休息、静下心来读书。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前环境正常，适合工作。");
                }else if(list.get(3).isMeetStandard()){
                    gasDetailObj.setHealthTips("环境嘈杂，长期暴露在此环境中，会妨碍睡眠，令人感到难过、焦虑，内分泌腺体功能紊乱,并出现精神紧张和内分泌系统失调请找到噪声源并关闭，如果噪声源在外面，请及时关窗。");
                }else if(list.get(4).isMeetStandard()){
                    gasDetailObj.setHealthTips("耳朵发痒、疼痛，严重影响睡眠质量，并会导致头晕、头痛、失眠多梦、记忆力减退、注意力不集中等神经衰弱症状和恶心、欲吐、胃痛、腹胀、食欲呆滞等消化道症状，尤其对孕妇及胎儿造成重大影响，请立即噪声源并关闭，或关窗，降低噪声。");
                }

                break;
            case Constant.GAS_CO2:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("主要来源有三个：一是人体动植物呼吸作用；二是化石物燃料的燃烧；三是微生物的分解作用。");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内co2含量非常低，空气优。");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("CO2含量符合标准，室内环境一般。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前室内CO2含量高，已经处于污染范围，请您注意室内空气状况，及时开窗通风。");
                }else if(list.get(3).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前室内CO2含量过高，长时间暴露在该环境下会让人觉得头疼、嗜睡、注意力无法集中等问题，请您马上开窗通风，缓解室内情况。");
                }else if(list.get(4).isMeetStandard()){
                    gasDetailObj.setHealthTips("CO2含量已经爆表了，请您马上开窗通风！过重的co2含量易造成大脑损伤！适当种些绿植有利于调节室内CO2哦！");
                }

                break;
            case Constant.GAS_PM25:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("自然来源：风扬尘土、火山灰、森林火灾、漂浮的海盐、花粉、真菌孢子、细菌。人为来源包括：道路扬尘、建筑施工扬尘、工业粉尘、厨房烟");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("PM2.5含量极低，适合您在室内放松休息。");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内PM2.5含量属于正常范围，对您和家人的健康基本无影响。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前室内PM2.5含量已经处于污染边缘，会对老人与小孩造成影响，请您及时开窗或打开空气净化器，保持室内空气优");
                }else if(list.get(3).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前室内PM2.5含量已经超标，对您在室内的家庭成员造成身体危害，请您注意室内空气情况，并及时开窗通风或打开空气净化器。");
                }else if(list.get(4).isMeetStandard()){
                    gasDetailObj.setHealthTips("PM2.5含量已经爆表，应当引起您的重视！高浓度的PM2.5环境下会大大增加致癌机率，同时增大心血管疾病的发病率，为了您和家人的健康，请及时开窗通风或打开空气净化器。");
                }
                break;
            case Constant.GAS_VOC:
                gasDetailObj.setList(list);
                gasDetailObj.setSource("VOC室外主要来自燃料燃烧和交通运输；室内主要来自燃煤和天然气等燃烧产物、吸烟、采暖和烹调等得烟雾，建筑和装饰材料、家具、家用电器、洗涤剂、清洁剂、衣物柔顺剂、化妆品、办公用品、壁纸及其他装饰品和人体本身的排放等。");
                if(list.get(0).isMeetStandard()){
                    gasDetailObj.setHealthTips("您家中的VOC含量极低，属于正常范围，对人体健康基本无影响。");
                }else if(list.get(1).isMeetStandard()){
                    gasDetailObj.setHealthTips("当前VOC处于污染边缘，对儿童、老人会产生影响哦，请及时开窗通风或打开空气净化器。");
                }else if(list.get(2).isMeetStandard()){
                    gasDetailObj.setHealthTips("VOC浓度含量已经超标，对您和您的家人造成重要的影响，请您马上开窗通风或打开空气净化器。");
                }else if(list.get(3).isMeetStandard()){
                    gasDetailObj.setHealthTips("室内VOC浓度严重超标，请立刻开窗或打开空气净化器，长时间暴露在这种环境中会严重损伤肝脏，造成内出血。");
                }

                break;
            default:
                break;

        }

        return gasDetailObj;
    }

    public static String getNoise(String value){

        if(value.equals("1")){
            return "优";
        }if(value.equals("2")){
            return "良";
        }else if(value.equals("3")){
            return "轻度污染";
        }else if(value.equals("4")){
            return "中度污染";
        }else if(value.equals("5")){
            return "重度污染";
        }
        return "";
    }

    public static String getVoc(String value){

        if(value.equals("0")){
            return "优";
        }if(value.equals("1")){
            return "轻度污染";
        }else if(value.equals("2")){
            return "中度污染";
        }else if(value.equals("3")){
            return "重度污染";
        }
        return "";
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
                indexLevelObj1.setTag("优");
                indexLevelObj1.setDescription("对人体无太大影响，能够正常生活");
                indexLevelObj1.setValue("0mg/m³-0.025mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("良");
                indexLevelObj2.setDescription("对正常人无太大影响，儿童、老人及呼吸疾病患者会发生轻微气喘。");
                indexLevelObj2.setValue("0.025-0.04mg/m³");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("轻度污染");
                indexLevelObj3.setDescription("有异味，正常人会感到刺激眼睛、头晕等不适");
                indexLevelObj3.setValue("0.04-0.125mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("中度污染");
                indexLevelObj4.setDescription("刺激眼睛，引起流泪，会出现恶心、胸闷等症状");
                indexLevelObj4.setValue("0.125-0.275mg/m³");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("重度污染");
                indexLevelObj5.setDescription("可引起恶心呕吐，咳嗽胸闷，气喘甚至肺水肿");
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

                if(level.equals("优")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("良")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("轻度污染")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("中度污染")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("重度污染")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_SHIDU:
                indexLevelObj1.setTag("干燥");
                indexLevelObj1.setDescription("室内环境干燥");
                indexLevelObj1.setValue("低于40%");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("适宜");
                indexLevelObj2.setDescription("室内湿度符合健康标准");
                indexLevelObj2.setValue("40-70%");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("潮湿");
                indexLevelObj3.setDescription("室内湿度偏高");
                indexLevelObj3.setValue("高于70%");
                indexLevelObj3.setIsMeetStandard(false);

              /*  indexLevelObj4.setTag("潮湿");
                indexLevelObj4.setDescription("室内较湿润，请打开空调进行调节");
                indexLevelObj4.setValue("70-75%");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("很潮湿");
                indexLevelObj5.setDescription("室内过于湿润，高湿度的环境会同样会 导致中暑，请您立刻打开空调");
                indexLevelObj5.setValue("75-100%");
                indexLevelObj5.setIsMeetStandard(false);*/

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

                if(level.equals("干燥")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("适宜")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("潮湿")){
                    indexLevelObj3.setIsMeetStandard(true);
                }/*else if(level.equals("潮湿")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("很潮湿")){
                    indexLevelObj5.setIsMeetStandard(true);
                }*/

                break;
            case Constant.GAS_WENDU:
                indexLevelObj1.setTag("偏低");
                indexLevelObj1.setDescription("室内温度偏低");
                indexLevelObj1.setValue("低于16℃");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("舒适");
                indexLevelObj2.setDescription("室内温度符合健康标准");
                indexLevelObj2.setValue("22-28℃");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("偏高");
                indexLevelObj3.setDescription("室内温度偏高");
                indexLevelObj3.setValue("大于28℃");
                indexLevelObj3.setIsMeetStandard(false);

              /*  indexLevelObj4.setTag("炎热");
                indexLevelObj4.setDescription("室内炎热，请打开空调调整室内温度");
                indexLevelObj4.setValue("32-35℃");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("高温");
                indexLevelObj5.setDescription("室温过高，在温度过高的环境下容易导 致中暑，请立刻打开空调");
                indexLevelObj5.setValue(">36℃");
                indexLevelObj5.setIsMeetStandard(false);*/

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

                if(level.equals("偏低")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("舒适")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("偏高")){
                    indexLevelObj3.setIsMeetStandard(true);
                }/*else if(level.equals("炎热")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("高温")){
                    indexLevelObj5.setIsMeetStandard(true);
                }*/

                break;
            case Constant.GAS_ZAOYIN:
                indexLevelObj1.setTag("安静");
                indexLevelObj1.setDescription("轻声细语，适合休息");
                indexLevelObj1.setValue("0-45分贝（白天）/0-30（夜间）");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("舒适");
                indexLevelObj2.setDescription("白天正常生活");
                indexLevelObj2.setValue("45-50分贝（白天）/30-45（夜间）");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("喧闹");
                indexLevelObj3.setDescription("正常生活，对正常人无较大影响，孕妇、儿童会有不舒服");
                indexLevelObj3.setValue("50-55分贝（白天）/45-50（夜间）");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("吵闹");
                indexLevelObj4.setDescription("环境嘈杂，长期在这类环境下会影响到您和家人的日常生活");
                indexLevelObj4.setValue("56-65分贝（白天）/50-60（夜间）");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("刺耳");
                indexLevelObj5.setDescription("当前环境过于吵闹，对于您和家人尤其是老人和小孩，会产生恶劣的影响。");
                indexLevelObj5.setValue("65分贝以上（白天）/60分贝以上（夜间）");
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
                indexLevelObj1.setTag("优");
                indexLevelObj1.setDescription("空气优，呼吸顺畅");
                indexLevelObj1.setValue("0-350ppm");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("良");
                indexLevelObj2.setDescription("一般环境");
                indexLevelObj2.setValue("351-600ppm");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("轻度污染");
                indexLevelObj3.setDescription("感觉空气轻度污染，并开始觉得昏昏欲睡");
                indexLevelObj3.setValue("600-1000ppm");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("中度污染");
                indexLevelObj4.setDescription("感觉头痛、嗜睡、呆滞、注意力无法集中、心跳加速、轻度恶心");
                indexLevelObj4.setValue("1000～1500ppm");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("重度污染");
                indexLevelObj5.setDescription("可能导致严重缺氧，造成永久性脑损伤、昏迷、甚至死亡");
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

                if(level.equals("优")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("良")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("轻度污染")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("中度污染")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("重度污染")){
                    indexLevelObj5.setIsMeetStandard(true);
                }


                break;
            case Constant.GAS_PM25:
                indexLevelObj1.setTag("优");
                indexLevelObj1.setDescription("PM2.5含量极低，室内空气洁净，对人体无害");
                indexLevelObj1.setValue("0mg/m³-0.025mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("良");
                indexLevelObj2.setDescription("对人体健康基本无影响");
                indexLevelObj2.setValue("0.025-0.075mg/m³");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("轻度污染");
                indexLevelObj3.setDescription("正常范围，正常人无太大影响，儿童、老人会有些许不适");
                indexLevelObj3.setValue("0.076mg/m³-0.15mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("中度污染");
                indexLevelObj4.setDescription("长时间在该浓度下生活，会感到眼部、呼吸系统发炎。");
                indexLevelObj4.setValue("0.15mg/m³-0.35mg/m³");
                indexLevelObj4.setIsMeetStandard(false);

                indexLevelObj5.setTag("重度污染");
                indexLevelObj5.setDescription("当人长时间生活在该浓度时，会增加致癌几率，同时心脏疾病几率增加。");
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

                if(level.equals("优")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("良")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("轻度污染")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("中度污染")){
                    indexLevelObj4.setIsMeetStandard(true);
                }else if(level.equals("重度污染")){
                    indexLevelObj5.setIsMeetStandard(true);
                }

                break;
            case Constant.GAS_VOC:
                indexLevelObj1.setTag("优");
                indexLevelObj1.setDescription("含量低，室内空气洁净，对人体无害");
                indexLevelObj1.setValue("0mg/m³-0.3mg/m³");
                indexLevelObj1.setIsMeetStandard(false);

                indexLevelObj2.setTag("轻度污染");
                indexLevelObj2.setDescription("临界值，正常人无太大影响，儿童、老人会有些许不适。");
                indexLevelObj2.setValue("0.3mg/m³-0.4mg/m³");
                indexLevelObj2.setIsMeetStandard(false);

                indexLevelObj3.setTag("中度污染");
                indexLevelObj3.setDescription("产生刺激和不适，可能出现头晕、头痛、嗜睡、无力、胸闷等自觉症状。");
                indexLevelObj3.setValue("0.4mg/m³-0.8mg/m³");
                indexLevelObj3.setIsMeetStandard(false);

                indexLevelObj4.setTag("重度污染");
                indexLevelObj4.setDescription("长时间会感到眼部、呼吸系统发炎，造成咳嗽鼻炎、加重哮喘导致支气管炎等");
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

                if(level.equals("优")){
                    indexLevelObj1.setIsMeetStandard(true);
                }else if(level.equals("轻度污染")){
                    indexLevelObj2.setIsMeetStandard(true);
                }else if(level.equals("中度污染")){
                    indexLevelObj3.setIsMeetStandard(true);
                }else if(level.equals("重度污染")){
                    indexLevelObj4.setIsMeetStandard(true);
                }

                break;
            default:
                break;
        }

        list.add(indexLevelObj1);
        list.add(indexLevelObj2);
        list.add(indexLevelObj3);

        if(gas != Constant.GAS_WENDU &&gas != Constant.GAS_SHIDU && indexLevelObj4.getTag()!=null){
            list.add(indexLevelObj4);
        }

        if(gas != Constant.GAS_VOC && indexLevelObj5.getTag()!=null){
            list.add(indexLevelObj5);
        }

        return list;
    }

}

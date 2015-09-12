package com.aigo.kt03airdemo.business;

import com.aigo.kt03airdemo.business.db.DbAirIndexObject;
import com.aigo.kt03airdemo.business.kt03.KT03AirIndex;
import com.aigo.kt03airdemo.business.kt03.KT03AirIndexObject;
import com.aigo.kt03airdemo.business.kt03.KT03ResultObject;
import com.aigo.kt03airdemo.business.ui.AirIndex;
import com.aigo.kt03airdemo.business.ui.AirQuality;
import com.aigo.kt03airdemo.business.ui.Result;
import com.aigo.kt03airdemo.business.ui.ResultObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangcirui on 15/7/16.
 */
public class KT03Adapter {

    private static KT03Adapter mKT03Adapter;

    public static KT03Adapter getInstance() {

        if (mKT03Adapter == null) {
            mKT03Adapter = new KT03Adapter();
        }
        return mKT03Adapter;
    }



    public ResultObject getResultObject(KT03ResultObject netResultObject){

        ResultObject resultObject = new ResultObject();
        Result result = new Result();
        result.setInfo(netResultObject.getResult().getInfo());
        result.setRet(netResultObject.getResult().getRet());
        resultObject.setResult(result);

     return  resultObject;
    }

    public AirIndex getAirIndex(DbAirIndexObject dbAirIndexObject) {

        AirIndex airIndex = new AirIndex();
        airIndex.setTemperature(dbAirIndexObject.getTemperature());
        airIndex.setHumidity(dbAirIndexObject.getHumidity());
        airIndex.setNoise(dbAirIndexObject.getNoise());
        airIndex.setCo2(dbAirIndexObject.getCo2());
        airIndex.setVoc(dbAirIndexObject.getVoc());
        airIndex.setPm25(dbAirIndexObject.getPm25());
        airIndex.setFormadehyde(dbAirIndexObject.getFormadehyde());
        airIndex.setTime(Long.parseLong(dbAirIndexObject.getPubtime()));

        return airIndex;
    }

    public DbAirIndexObject getDbAirIndexObject(AirIndex airIndex) {
        DbAirIndexObject dbAirIndexObject = new DbAirIndexObject();
        dbAirIndexObject.setTemperature(airIndex.getTemperature());
        dbAirIndexObject.setHumidity(airIndex.getHumidity());
        dbAirIndexObject.setNoise(airIndex.getNoise());
        dbAirIndexObject.setCo2(airIndex.getCo2());
        dbAirIndexObject.setVoc(airIndex.getVoc());
        dbAirIndexObject.setPm25(airIndex.getPm25());
        dbAirIndexObject.setFormadehyde(airIndex.getFormadehyde());
        dbAirIndexObject.setPubtime(airIndex.getTime()+"");
        return dbAirIndexObject;
    }



    public AirIndex getAirIndex(KT03AirIndexObject netAirIndexObject) {
        KT03AirIndex netAirIndex = netAirIndexObject.getData();
        AirIndex airIndex = new AirIndex();
        airIndex.setTemperature(netAirIndex.getTemperature());
        airIndex.setHumidity(netAirIndex.getHumidity());
        airIndex.setNoise(netAirIndex.getNoise());
        airIndex.setCo2(netAirIndex.getCo2());
        airIndex.setVoc(netAirIndex.getVoc());
        airIndex.setPm25(netAirIndex.getPm25());
        airIndex.setFormadehyde(netAirIndex.getFormadehyde());
        airIndex.setTime(netAirIndexObject.getPubtime());
        airIndex.setIaq(getIaq(netAirIndexObject));
        airIndex.setIaqQuality(iaqToComfort(getIaq(netAirIndexObject)));
        return airIndex;
    }



    public AirQuality getAirIndexToAirQuality(AirIndex netAirIndex) {

        AirQuality airQuality = new AirQuality();
        airQuality.setTemperature(temperatureToComfort(netAirIndex.getTemperature()));
        airQuality.setHumidity(humidityToComfort(netAirIndex.getHumidity()));
        airQuality.setNoise(noiseToComfort(netAirIndex.getNoise()));
        airQuality.setCo2(co2ToComfort(netAirIndex.getCo2()));
        airQuality.setVoc(vocToComfort(netAirIndex.getVoc()));
        airQuality.setPm25(pm25ToComfort(netAirIndex.getPm25()));
        airQuality.setFormadehyde(formadehydeToComfort(netAirIndex.getFormadehyde()));
        airQuality.setTime(netAirIndex.getTime());
        airQuality.setIaq(netAirIndex.getIaq());
        airQuality.setIaqQuality(iaqToComfort(netAirIndex.getIaq()));

        return airQuality;
    }

    public AirQuality getAirQuality(KT03AirIndexObject netAirIndexObject) {
        KT03AirIndex netAirIndex = netAirIndexObject.getData();

        AirQuality airQuality = new AirQuality();

        airQuality.setTemperature(temperatureToComfort(netAirIndex.getTemperature()));
        airQuality.setHumidity(humidityToComfort(netAirIndex.getHumidity()));
        airQuality.setNoise(noiseToComfort(netAirIndex.getNoise()));
        airQuality.setCo2(co2ToComfort(netAirIndex.getCo2()));
        airQuality.setVoc(vocToComfort(netAirIndex.getVoc()));
        airQuality.setPm25(pm25ToComfort(netAirIndex.getPm25()));
        airQuality.setFormadehyde(formadehydeToComfort(netAirIndex.getFormadehyde()));
        airQuality.setTime(netAirIndexObject.getPubtime());
        airQuality.setIaq(getIaq(netAirIndexObject));
        airQuality.setIaqQuality(iaqToComfort(getIaq(netAirIndexObject)));

        return airQuality;
    }

    public String getIaq(KT03AirIndexObject netAirIndexObject){
        float co2 = 0 ;
        float formadehyde= 0 ;
        float humidity= 0 ;
        float noise= 0 ;
        float voc= 0 ;
        float tem= 0 ;
        float pm25= 0 ;

        KT03AirIndex netAirIndex = netAirIndexObject.getData();
        long  currentTime = netAirIndexObject.getPubtime();

        try{

             co2 = Float.parseFloat(netAirIndex.getCo2());
             formadehyde = Float.parseFloat(netAirIndex.getFormadehyde());
             humidity = Float.parseFloat(netAirIndex.getHumidity());
             noise = Float.parseFloat(netAirIndex.getNoise());

             voc = Float.parseFloat(netAirIndex.getVoc());
             tem = Float.parseFloat(netAirIndex.getTemperature());
             pm25 = Float.parseFloat(netAirIndex.getPm25());

        }catch (Exception e){
            e.printStackTrace();
        }


        List<Float> list = new ArrayList<Float>();

        float c = 0;
        float f = 0;
        float h = 0;
        float n = 0;
        float v = 0;
        float p = 0;
        float t = 0;

        if(co2<=350){
            c = co2/350;
        }else if(co2<=600){
            c = co2/600;
        }else if(co2<1000){
            c = co2/1000;
        }else if(co2<1500){
            c = co2/1500;
        }else{
            c = co2/1500;
        }

        list.add(c);

        if(formadehyde<0.025){
            f = formadehyde/(float)0.025;
        }else if(formadehyde<0.04){
            f = formadehyde/(float)0.04;
        }else if(formadehyde<0.125){
            f = formadehyde/(float)0.125;
        }else if(formadehyde<0.275){
            f = formadehyde/(float)0.275;
        }else{
            f = formadehyde/(float)0.275;
        }

        list.add(f);



        if(noise < 45){
            n = noise/45;
        }else if(noise< 50){
            n = noise/50;
        }else if(noise< 55){
            n = noise/55;
        }else if(noise< 65){
            n = noise/65;
        }else{
            n = noise/65;
        }

        list.add(n);

        if(voc<0.3){
            v = voc/(float)0.3;
        }else if(voc < 0.4){
            v = voc/(float)0.4;
        }else if(voc <= 0.8){
            v = voc/(float)0.8;
        }else {
            v = voc/(float)0.8;
        }
        list.add(v);

        if(pm25<0.025){
            p = pm25/(float)0.025;
        }else if(pm25<=0.075){
            p = pm25/(float)0.075;
        }else if(pm25<0.15){
            p = pm25/(float)0.15;
        }else if(pm25<0.35){
            p = pm25/(float)0.35;
        }else{
            p = pm25/(float)0.35;
        }

        list.add(p);

        if(tem>20 && tem<22){
            t = tem/(float)22;
        }else if(tem > 22 && tem <26){
            t = tem/(float)26;
        }else if(tem > 27 && tem <29){
            t = tem/(float)29;
        }else if(tem > 29 && tem <32){
            t = tem/(float)32;
        }else if(tem > 32 && tem <35){
            t = tem/(float)35;
        }else{
            t = tem/36;
        }


        list.add(t);
        if(humidity > 0 && humidity<35){
            h = humidity/35;
        }else if(humidity > 35 && humidity<40){
            h = humidity/40;
        }else if(humidity > 40 && humidity<45){
            h = humidity/45;
        }else if(humidity > 45 && humidity<50){
            h = humidity/50;
        }else if(humidity > 50 && humidity<55){
            h = humidity/55;
        }else if(humidity > 55 && humidity<65){
            h = humidity/65;
        }else if(humidity > 65 && humidity<70){
            h = humidity/70;
        }else if(humidity > 70 && humidity<75){
            h = humidity/75;
        }else if(humidity > 75 && humidity<100){
            h = humidity/100;
        }

        list.add(h);

        float max = Collections.max(list);

        float iaq = (float)Math.sqrt(max * (c + f + h + n + v + p + t));

        DecimalFormat decimalFormat = new DecimalFormat("##0.00");

        return decimalFormat.format(iaq);
    }




    public String iaqToComfort(String iaq){
        float val = 0;
        try{
            val = Float.parseFloat(iaq);
        }catch (Exception e){

        }
        if(val <= 0.49){
            return "清洁";
        }
        if(val>=0.50 && val<=0.99){
            return "未污染";
        }
        if(val>=1.00 && val<=1.49){
            return "轻度污染";
        }
        if(val>=1.50 && val<=1.99){
            return "中度污染";
        }
        if(val>=2.00){
            return "重度污染";
        }

        return "";
    }

    public  String noiseToComfort(String noise){

        int val = Integer.parseInt(noise);
        if(val>0 && val<45){
            return "安静";
        }
        if(val>45 && val<50){
            return "舒适";
        }
        if(val>50 && val<55){
            return "喧闹";
        }
        if(val>56 && val<65){
            return "吵闹";
        }
        if(val>65){
            return "刺耳";
        }

        return "";
    }
    public  String co2ToComfort(String co2){

        int val = Integer.parseInt(co2);
        if(val>0 && val<350){
            return "清新";
        }
        if(val>351 && val<600){
            return "较清新";
        }
        if(val>600 && val<1000){
            return "浑浊";
        }
        if(val>1000 && val<1500){
            return "很浑浊";
        }
        if(val>1500){
            return "窒息";
        }

        return "";
    }
    public  String vocToComfort(String voc){

        int val = Integer.parseInt(voc);
        if(val==1){
            return "清新";
        }

        if(val==2){
            return "浑浊";
        }
        if(val==3){
            return "很浑浊";
        }
        if(val==4){
            return "窒息";
        }

        return "";
    }

    public  String pm25ToComfort(String pm25){

        float val = 0;
        try{
            val = Float.parseFloat(pm25);
        }catch (Exception e){

        }
        if(val>0 && val<0.025){
            return "清新";
        }
        if(val>0.025 && val<0.075){
            return "较清新";
        }
        if(val>0.076 && val<0.15){
            return "浑浊";
        }
        if(val>0.15 && val<0.35){
            return "很浑浊";
        }
        if(val>0.35){
            return "窒息";
        }

        return "";
    }
    public  String formadehydeToComfort(String formadehyde){

        float val = 0;
        try{
            val = Float.parseFloat(formadehyde);
        }catch (Exception e){

        }
        if(val>0 && val<0.025){
            return "清新";
        }
        if(val>0.025 && val<0.04){
            return "较清新";
        }
        if(val>0.04 && val<0.125){
            return "浑浊";
        }
        if(val>0.125 && val<0.275){
            return "很浑浊";
        }
        if(val>0.275){
            return "窒息";
        }

        return "";
    }




    public String humidityToComfort(String humidity){
        try {
            String sDt1 = "2015";
            String sDt2 = "2015";
            String sDt3 = "2015";
            String sDt4 = "2015";

            long time =System.currentTimeMillis();
            SimpleDateFormat dataFormat= new SimpleDateFormat("yyyy");
            String  yearsString = dataFormat.format(time);

            int year = Integer.parseInt(yearsString);
            if (year % 4 == 0 && year % 100!=0||year%400==0) {
                sDt1 = "03/20/"+yearsString+" 00:00:00";
                sDt2 = "06/21/"+yearsString+" 00:00:00";
                sDt3 = "09/22/"+yearsString+" 00:00:00";
                sDt4 = "12/21/"+yearsString+" 00:00:00";
            }
            else {
                sDt1 = "03/21/"+yearsString+" 00:00:00";
                sDt2 = "06/22/"+yearsString+" 00:00:00";
                sDt3 = "09/23/"+yearsString+" 00:00:00";
                sDt4 = "12/22/"+yearsString+" 00:00:00";
            }

            SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dt1 = sdf.parse(sDt1);//春
            Date dt2 = sdf.parse(sDt2);//夏
            Date dt3 = sdf.parse(sDt3);//秋
            Date dt4 = sdf.parse(sDt4);//冬
            //继续转换得到秒数的long型
            long lTime1 = dt1.getTime();
            long lTime2 = dt2.getTime();
            long lTime3 = dt3.getTime();
            long lTime4 = dt4.getTime();

            long currentTime = System.currentTimeMillis();
            int tem = Integer.parseInt(humidity);

            if(currentTime>lTime4){

                if(tem>=40 && tem<=50){
                    return "适宜";
                }
                if(tem>=50 && tem<55 || tem>=35 && tem <=40){
                    return "较适宜";
                }
                if(tem>=55 && tem<=60){
                    return "较潮湿";
                }
                if(tem>=30 && tem<=35){
                    return "较干燥";
                }
                if(tem>60 && tem<65){
                    return "潮湿";
                }
                if(tem>=35 && tem<=40){
                    return "干燥";
                }
                if(tem>65 && tem<100){
                    return "很潮湿";
                }
                if(tem>0 && tem<35){
                    return "很干燥";
                }

            }else if(currentTime>lTime3){

                if(tem>=45 && tem<=55){
                    return "适宜";
                }
                if(tem>=55 && tem<60 || tem>=40 && tem <=45){
                    return "较适宜";
                }
                if(tem>=60 && tem<=65){
                    return "较潮湿";
                }
                if(tem>=35 && tem<=40){
                    return "较干燥";
                }
                if(tem>65 && tem<70){
                    return "潮湿";
                }
                if(tem>=30 && tem<=35){
                    return "干燥";
                }
                if(tem>70 && tem<100){
                    return "很潮湿";
                }
                if(tem>0 && tem<30){
                    return "很干燥";
                }

            }else if(currentTime>lTime2){

                if(tem>=50 && tem<=60){
                    return "适宜";
                }
                if(tem>=55 && tem<65 || tem>=45 && tem <=50){
                    return "较适宜";
                }
                if(tem>=65 && tem<=70){
                    return "较潮湿";
                }
                if(tem>=40 && tem<=45){
                    return "较干燥";
                }
                if(tem>70 && tem<75){
                    return "潮湿";
                }
                if(tem>=35 && tem<=40){
                    return "干燥";
                }
                if(tem>75 && tem<100){
                    return "很潮湿";
                }
                if(tem>0 && tem<35){
                    return "很干燥";
                }
            }else if(currentTime>lTime1){

                if(tem>=45 && tem<=55){
                    return "适宜";
                }
                if(tem>=55 && tem<60 || tem>=40 && tem <=45){
                    return "较适宜";
                }
                if(tem>=60 && tem<=65){
                    return "较潮湿";
                }
                if(tem>=35 && tem<=40){
                    return "较干燥";
                }
                if(tem>65 && tem<70){
                    return "潮湿";
                }
                if(tem>=30 && tem<=35){
                    return "干燥";
                }
                if(tem>70 && tem<100){
                    return "很潮湿";
                }
                if(tem>0 && tem<30){
                    return "很干燥";
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String temperatureToComfort(String temperature) {
        try {
            String sDt1 = "2015";
            String sDt2 = "2015";
            String sDt3 = "2015";
            String sDt4 = "2015";

            long time =System.currentTimeMillis();
            SimpleDateFormat dataFormat= new SimpleDateFormat("yyyy");
            String  yearsString = dataFormat.format(time);

            int year = Integer.parseInt(yearsString);
            if (year % 4 == 0 && year % 100!=0||year%400==0) {
                sDt1 = "03/20/"+yearsString+" 00:00:00";
                sDt2 = "06/21/"+yearsString+" 00:00:00";
                sDt3 = "09/22/"+yearsString+" 00:00:00";
                sDt4 = "12/21/"+yearsString+" 00:00:00";
            }
            else {
                sDt1 = "03/21/"+yearsString+" 00:00:00";
                sDt2 = "06/22/"+yearsString+" 00:00:00";
                sDt3 = "09/23/"+yearsString+" 00:00:00";
                sDt4 = "12/22/"+yearsString+" 00:00:00";
            }

            SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dt1 = sdf.parse(sDt1);
            Date dt2 = sdf.parse(sDt2);
            Date dt3 = sdf.parse(sDt3);
            Date dt4 = sdf.parse(sDt4);
            //继续转换得到秒数的long型
            long lTime1 = dt1.getTime();
            long lTime2 = dt2.getTime();
            long lTime3 = dt3.getTime();
            long lTime4 = dt4.getTime();

            long currentTime = System.currentTimeMillis();
            float tem = 0;
            try{
                 tem = Float.parseFloat(temperature);
            }catch (Exception e){

            }

            if(currentTime>lTime4){

                if(tem>=21 && tem<=23){
                    return "适宜";
                }
                if(tem>=18 && tem<=20 || tem>=23 && tem <=25){
                    return "较适宜";
                }
                if(tem>=15 && tem<=17){
                    return "较冷";
                }
                if(tem>=12 && tem<=14){
                    return "寒冷";
                }
                if(tem<12){
                    return "过于寒冷";
                }

            }else if(currentTime>lTime3){

                if(tem>=18 && tem<=24){
                    return "适宜";
                }
                if(tem>=24 && tem<=26){
                    return "较适宜";
                }
                if(tem>=27 && tem<=30 ){
                    return "较热";
                }
                if(tem>=31 && tem<=34){
                    return "炎热";
                }
                if(tem >35){
                    return "过于炎热";
                }
                if(tem>=15 && tem<=17 ){
                    return "较冷";
                }
                if(tem>=12 && tem<=15){
                    return "寒冷";
                }
                if(tem < 12){
                    return "过于寒冷";
                }

            }else if(currentTime>lTime2){

                if(tem>=22 && tem<=26){
                    return "适宜";
                }
                if(tem>=20 && tem<=22 || tem>=27 && tem <=29){
                    return "较适宜";
                }
                if(tem>=29 && tem<=32){
                    return "较热";
                }
                if(tem>=32 && tem<=35){
                    return "炎热";
                }
                if(tem >36){
                    return "过于炎热";
                }

            }else if(currentTime>lTime1){

                if(tem>=18 && tem<=24){
                    return "适宜";
                }
                if(tem>=24 && tem<=26){
                    return "较适宜";
                }
                if(tem>=27 && tem<=30 ){
                    return "较热";
                }
                if(tem>=31 && tem<=34){
                    return "炎热";
                }
                if(tem >35){
                    return "过于炎热";
                }
                if(tem>=15 && tem<=17 ){
                    return "较冷";
                }
                if(tem>=12 && tem<=15){
                    return "寒冷";
                }
                if(tem < 12){
                    return "过于寒冷";
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

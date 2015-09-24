package com.aigo.kt03airdemo.business;

import android.util.Log;

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
        airIndex.setId(dbAirIndexObject.getId());
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
        dbAirIndexObject.setId(airIndex.getId());
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

    public String getIaq(KT03AirIndexObject netAirIndexObject) {

        long lTime1 = 0;
        long lTime2 = 0;
        long lTime3 = 0;
        long lTime4 = 0;

        try {
            String sDt1 = "2015";
            String sDt2 = "2015";
            String sDt3 = "2015";
            String sDt4 = "2015";

            long time = System.currentTimeMillis();
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy");
            String yearsString = dataFormat.format(time);

            sDt1 = "03/01/" + yearsString + " 00:00:00";
            sDt2 = "06/01/" + yearsString + " 00:00:00";
            sDt3 = "09/01/" + yearsString + " 00:00:00";
            sDt4 = "12/01/" + yearsString + " 00:00:00";


            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dt1 = sdf.parse(sDt1);//春
            Date dt2 = sdf.parse(sDt2);//夏
            Date dt3 = sdf.parse(sDt3);//秋
            Date dt4 = sdf.parse(sDt4);//冬
            //继续转换得到秒数的long型
            lTime1 = dt1.getTime();
            lTime2 = dt2.getTime();
            lTime3 = dt3.getTime();
            lTime4 = dt4.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }


        float co2 = 0;
        float formadehyde = 0;
        float humidity = 0;
        float noise = 0;
        float voc = 0;
        float tem = 0;
        float pm25 = 0;

        KT03AirIndex netAirIndex = netAirIndexObject.getData();
        long currentTime = netAirIndexObject.getPubtime();

        try {
            co2 = Float.parseFloat(netAirIndex.getCo2());
            formadehyde = Float.parseFloat(netAirIndex.getFormadehyde());
            humidity = Float.parseFloat(netAirIndex.getHumidity());
            noise = Float.parseFloat(netAirIndex.getNoise());

            voc = Float.parseFloat(netAirIndex.getVoc());
            tem = Float.parseFloat(netAirIndex.getTemperature());
            pm25 = Float.parseFloat(netAirIndex.getPm25());

        } catch (Exception e) {
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

        c = co2 / 600;

        list.add(c);

        f = formadehyde / (float) 0.04;

        list.add(f);

        n = noise/2;

        list.add(n);

        v = voc/1;

        list.add(v);

        p = pm25 / (float)0.075/1000;

        list.add(p);

        if (currentTime > lTime4) {
            t = tem / (float) 20;
            h = humidity / 45;
        } else if (currentTime > lTime3) {
            t = tem / (float) 22;
            h = humidity / 55;
        } else if (currentTime > lTime2) {
            t = tem / (float) 25;
            h = humidity / 60;
        } else if (currentTime > lTime1) {
            t = tem / (float) 22;
            h = humidity / 55;
        }

        list.add(t);

        list.add(h);
        Log.d("max=", "max=" + netAirIndexObject.toString());
        float max = Collections.max(list);
        Log.d("max=", "max=" + max + " c=" + c + " f=" + f + " h=" + h + " n=" + n + " v=" + v + " p=" + p + " t=" + t);

        float iaq = (float) Math.sqrt(max * (c + f + h + n + v + p + t)/7);
        Log.d("max=","iaq="+iaq);

        DecimalFormat decimalFormat = new DecimalFormat("##0.00");

        return decimalFormat.format(iaq);
    }


    public String iaqToComfort(String iaq) {
        float val = 0;
        try {
            val = Float.parseFloat(iaq);
        } catch (Exception e) {

        }
        if  (val < 0.50) {
            return "优";
        } else if (val >= 0.50 && val < 1.00) {
            return "良";
        } else if (val >= 1.00 && val < 1.50) {
            return "轻度污染";
        } else if (val >= 1.50 && val < 2.00) {
            return "中度污染";
        } else if (val >= 2.00 && val < 3.00) {
            return "重度污染";
        } else if (val >= 3.00) {
            return "严重污染";
        }
        return "";
    }

    public String noiseToComfort(String noise) {

        int val = Integer.parseInt(noise);
        if (val == 1) {
            return "优";
        }
        if (val == 2) {
            return "优";
        }
        if (val == 3) {
            return "轻度污染";
        }
        if (val == 4) {
            return "中度污染";
        }
        if (val == 5) {
            return "重度污染";
        }

        return "";
    }

    public String co2ToComfort(String co2) {

        int val = Integer.parseInt(co2);
        if (val > 0 && val < 350) {
            return "优";
        }
        if (val > 351 && val < 600) {
            return "良";
        }
        if (val > 600 && val < 1000) {
            return "轻度污染";
        }
        if (val > 1000 && val < 1500) {
            return "中度污染";
        }
        if (val > 1500) {
            return "重度污染";
        }

        return "";
    }

    public String vocToComfort(String voc) {

        int val = Integer.parseInt(voc);

        if (val == 0) {
            return "优";
        }

        if (val == 1) {
            return "轻度污染";
        }
        if (val == 2) {
            return "中度污染";
        }
        if (val == 3) {
            return "重度污染";
        }

        return "";
    }

    public String pm25ToComfort(String pm25) {

        float val = 0;
        try {
            val = Float.parseFloat(pm25);
        } catch (Exception e) {

        }
        if (val > 0 && val < 0.025) {
            return "优";
        }
        if (val > 0.025 && val < 0.075) {
            return "良";
        }
        if (val > 0.076 && val < 0.15) {
            return "轻度污染";
        }
        if (val > 0.15 && val < 0.35) {
            return "中度污染";
        }
        if (val > 0.35) {
            return "重度污染";
        }

        return "";
    }

    public String formadehydeToComfort(String formadehyde) {

        float val = 0;
        try {
            val = Float.parseFloat(formadehyde);
        } catch (Exception e) {

        }
        if (val > 0 && val < 0.025) {
            return "优";
        }
        if (val > 0.025 && val < 0.04) {
            return "良";
        }
        if (val > 0.04 && val < 0.125) {
            return "轻度污染";
        }
        if (val > 0.125 && val < 0.275) {
            return "中度污染";
        }
        if (val > 0.275) {
            return "重度污染";
        }

        return "";
    }


    public String humidityToComfort(String humidity) {
        try {
            String sDt1 = "2015";
            String sDt2 = "2015";
            String sDt3 = "2015";
            String sDt4 = "2015";

            long time = System.currentTimeMillis();
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy");
            String yearsString = dataFormat.format(time);

            int year = Integer.parseInt(yearsString);
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                sDt1 = "03/20/" + yearsString + " 00:00:00";
                sDt2 = "06/21/" + yearsString + " 00:00:00";
                sDt3 = "09/22/" + yearsString + " 00:00:00";
                sDt4 = "12/21/" + yearsString + " 00:00:00";
            } else {
                sDt1 = "03/21/" + yearsString + " 00:00:00";
                sDt2 = "06/22/" + yearsString + " 00:00:00";
                sDt3 = "09/23/" + yearsString + " 00:00:00";
                sDt4 = "12/22/" + yearsString + " 00:00:00";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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

            if (currentTime > lTime4) {

                if (tem < 30) {
                    return "干燥";
                }
                if (tem >= 30 && tem <= 60) {
                    return "适宜";
                }
                if (tem > 60) {
                    return "潮湿";
                }


            } else if (currentTime > lTime3) {
                if (tem < 40) {
                    return "干燥";
                }
                if (tem >= 40 && tem <= 70) {
                    return "适宜";
                }
                if (tem > 70) {
                    return "潮湿";
                }

            } else if (currentTime > lTime2) {

                if (tem < 40) {
                    return "干燥";
                }
                if (tem >= 40 && tem <= 80) {
                    return "适宜";
                }
                if (tem > 80) {
                    return "潮湿";
                }
            } else if (currentTime > lTime1) {

                if (tem < 40) {
                    return "干燥";
                }
                if (tem >= 40 && tem <= 70) {
                    return "适宜";
                }
                if (tem > 70) {
                    return "潮湿";
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

            long time = System.currentTimeMillis();
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy");
            String yearsString = dataFormat.format(time);

            int year = Integer.parseInt(yearsString);
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                sDt1 = "03/20/" + yearsString + " 00:00:00";
                sDt2 = "06/21/" + yearsString + " 00:00:00";
                sDt3 = "09/22/" + yearsString + " 00:00:00";
                sDt4 = "12/21/" + yearsString + " 00:00:00";
            } else {
                sDt1 = "03/21/" + yearsString + " 00:00:00";
                sDt2 = "06/22/" + yearsString + " 00:00:00";
                sDt3 = "09/23/" + yearsString + " 00:00:00";
                sDt4 = "12/22/" + yearsString + " 00:00:00";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
            try {
                tem = Float.parseFloat(temperature);
            } catch (Exception e) {

            }

            if (currentTime > lTime4) {

                if (tem <= 16) {
                    return "偏低";
                } else if (tem > 16 && tem <= 24) {
                    return "舒适";
                } else if (tem > 24) {
                    return "偏高";
                }

            } else if (currentTime > lTime3) {

                if (tem <= 18) {
                    return "偏低";
                } else if (tem > 18 && tem <= 24) {
                    return "舒适";
                } else if (tem > 24) {
                    return "偏高";
                }


            } else if (currentTime > lTime2) {

                if (tem <= 22) {
                    return "偏低";
                } else if (tem > 22 && tem <= 28) {
                    return "舒适";
                } else if (tem > 28) {
                    return "偏高";
                }

            } else if (currentTime > lTime1) {

                if (tem <= 18) {
                    return "偏低";
                } else if (tem > 18 && tem <= 24) {
                    return "舒适";
                } else if (tem > 24) {
                    return "偏高";
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}

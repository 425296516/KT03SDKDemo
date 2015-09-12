package com.aigo.kt03airdemo.business.db;

/**
 * Created by zhangcirui on 15/9/3.
 */
public class DbAirIndexObject {

    private int id;
    private String temperature;
    private String humidity;
    private String noise;
    private String co2;
    private String voc;
    private String pm25;
    private String formadehyde;
    private String pubtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getVoc() {
        return voc;
    }

    public void setVoc(String voc) {
        this.voc = voc;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getFormadehyde() {
        return formadehyde;
    }

    public void setFormadehyde(String formadehyde) {
        this.formadehyde = formadehyde;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    @Override
    public String toString() {
        return "DbAirIndexObject{" +
                "id=" + id +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", noise='" + noise + '\'' +
                ", co2='" + co2 + '\'' +
                ", voc='" + voc + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", formadehyde='" + formadehyde + '\'' +
                ", pubtime=" + pubtime +
                '}';
    }
}

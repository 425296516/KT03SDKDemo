package com.aigo.kt03airdemo.business.ui;

/**
 * Created by zhangcirui on 15/7/16.
 */
public class AirQuality {

    private String temperature;
    private String humidity;
    private String noise;
    private String co2;
    private String voc;
    private String pm25;
    private String formadehyde;
    private String iaq;
    private String iaqQuality;
    private long time;

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

    public String getIaq() {
        return iaq;
    }

    public void setIaq(String iaq) {
        this.iaq = iaq;
    }

    public String getIaqQuality() {
        return iaqQuality;
    }

    public void setIaqQuality(String iaqQuality) {
        this.iaqQuality = iaqQuality;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AirIndex{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", noise='" + noise + '\'' +
                ", co2='" + co2 + '\'' +
                ", voc='" + voc + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", formadehyde='" + formadehyde + '\'' +
                ", iaq='" + iaq + '\'' +
                ", iaqQuality='" + iaqQuality + '\'' +
                ", time=" + time +
                '}';
    }
}
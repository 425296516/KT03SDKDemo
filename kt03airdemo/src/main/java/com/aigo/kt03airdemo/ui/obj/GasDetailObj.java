package com.aigo.kt03airdemo.ui.obj;

import java.util.List;

/**
 * Created by qinqi on 15/9/3.
 */
public class GasDetailObj {
    private int id;
    private List<IndexLevelObj> list;
    private String source;
    private String healthTips;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IndexLevelObj> getList() {
        return list;
    }

    public void setList(List<IndexLevelObj> list) {
        this.list = list;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHealthTips() {
        return healthTips;
    }

    public void setHealthTips(String healthTips) {
        this.healthTips = healthTips;
    }

    @Override
    public String toString() {
        return "GasDetailObj{" +
                "id=" + id +
                ", list=" + list +
                ", source='" + source + '\'' +
                ", healthTips='" + healthTips + '\'' +
                '}';
    }
}

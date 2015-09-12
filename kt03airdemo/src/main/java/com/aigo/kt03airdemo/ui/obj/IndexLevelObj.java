package com.aigo.kt03airdemo.ui.obj;

/**
 * Created by qinqi on 15/9/3.
 */
public class IndexLevelObj {
    private int id;
    private String tag;
    private String description;
    private String value;
    private boolean isMeetStandard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isMeetStandard() {
        return isMeetStandard;
    }

    public void setIsMeetStandard(boolean isMeetStandard) {
        this.isMeetStandard = isMeetStandard;
    }

    @Override
    public String toString() {
        return "IndexLevelObj{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                ", isMeetStandard=" + isMeetStandard +
                '}';
    }
}

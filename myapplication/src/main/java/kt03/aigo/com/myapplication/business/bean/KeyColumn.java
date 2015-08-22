package kt03.aigo.com.myapplication.business.bean;

/**
 * Created by zhangcirui on 15/8/22.
 */
public class KeyColumn {
    int id;
    String name;
    int device;
    int type;
    int key_column;
    String name_tran;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getKey_column() {
        return key_column;
    }
    public void setKey_column(int key_column) {
        this.key_column = key_column;
    }

    public int getDevice() {
        return device;
    }
    public void setDevice(int device) {
        this.device = device;
    }
    public String getName_tran() {
        return name_tran;
    }
    public void setName_tran(String name_tran) {
        this.name_tran = name_tran;
    }
    public KeyColumn(int id, String name, int device, int type, int key_column,
                     String name_tran) {
        super();
        this.id = id;
        this.name = name;
        this.device = device;
        this.type = type;
        this.key_column = key_column;
        this.name_tran = name_tran;
    }


    public KeyColumn(int id, String name, int device, int type, int key_column) {
        super();
        this.id = id;
        this.name = name;
        this.device = device;
        this.type = type;
        this.key_column = key_column;

    }

}
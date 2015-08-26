package jiuwei.kt03sdkdemo.library.bean;


import java.util.List;

public class ModelNum {

    private Integer id;

    private List<IRKey> keys;

    public ModelNum() {
    }

    public ModelNum(Integer id) {
        this.id = id;
      
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public List<IRKey> getKeys() {
		return keys;
	}

	public void setKeys(List<IRKey> keys) {
		this.keys = keys;
	}

	@Override
    public String toString() {
        return "ModelSearch{" +
                "id=" + id +
              
                '}';
    }
}

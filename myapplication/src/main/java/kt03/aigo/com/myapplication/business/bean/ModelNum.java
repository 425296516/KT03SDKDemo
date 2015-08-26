package kt03.aigo.com.myapplication.business.bean;


import java.util.List;

/**
 * 调用http://222.191.229.234:10068/SmartHomeServer/wyf/getSearchRemoteKey 接口获取到的数据集
 * */
public class ModelNum {

    private Integer id;//某种空调品牌的遥控器键码id

    private List<IRKey> keys;//某种空调品牌的遥控器键码集合

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

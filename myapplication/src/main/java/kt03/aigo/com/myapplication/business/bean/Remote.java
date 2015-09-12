package kt03.aigo.com.myapplication.business.bean;
import java.util.List;
import java.util.Map;


public class Remote {

	// **属性 *
	private String id; // ID

    private int type;

    private String name; // 名称

    private int room_no;    //房间编号，

    private int brand_id;

    private String model; // 类型

    private Brand brand;

    private List<IRKey> keys;

    private int learn;

    private List<IRKey> air_keys;
	
	public List<IRKey> getAir_keys() {
		return air_keys;
	}

	public void setAir_keys(List<IRKey> air_keys) {
		this.air_keys = air_keys;
	}

	public Remote()
	{
		this.room_no=-1; //若没有房间设定，则默认为-1
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getBrand_id()
	{
		return brand_id;
	}

	public void setBrand_id(int brand_id)
	{
		this.brand_id = brand_id;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public int getRoom_no()
	{
		return room_no;
	}

	public void setRoom_no(int room_no)
	{
		this.room_no = room_no;
	}

	public List<IRKey> getKeys()
	{
		return keys;
	}

	public void setKeys(List<IRKey> keys)
	{
		this.keys = keys;
	}

	public Brand getBrand()
	{
		return brand;
	}

	public void setBrand(Brand brand)
	{
		this.brand = brand;
	}

	public int getLearn() {
		return learn;
	}

	public void setLearn(int learn) {
		this.learn = learn;
	}


    @Override
    public String toString() {
        return "Remote{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", room_no=" + room_no +
                ", brand_id=" + brand_id +
                ", model='" + model + '\'' +
                ", brand=" + brand +
                ", keys=" + keys +
                ", learn=" + learn +
                ", air_keys=" + air_keys +
                '}';
    }
}

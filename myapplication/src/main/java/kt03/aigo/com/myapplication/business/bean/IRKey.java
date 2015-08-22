/**
 * 
 */
package kt03.aigo.com.myapplication.business.bean;

import java.util.List;

/**
 * @author jiangs
 */
public class IRKey {

	private long id;

	private String name; //按键名称，普通键可能无值，自定义键name一般都有名称，比如用户自定义了一个键“丽音”

	private int type; //按键类型

	private String remote_id ;//所属遥控器id

	private List<Infrared> infrareds;

	private int protocol;//按键协议，协议空调的协议，一般情况一个遥控器的按键协议都一样

	private int[] state;//按键协议，空调状态
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
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
	public String getRemote_id()
	{
		return remote_id;
	}
	public void setRemote_id(String remote_id)
	{
		this.remote_id = remote_id;
	}
	public List<Infrared> getInfrareds()
	{
		return infrareds;
	}
	public void setInfrareds(List<Infrared> infrareds)
	{
		this.infrareds = infrareds;
	}
	public int getProtocol()
	{
		return protocol;
	}
	public void setProtocol(int protocol)
	{
		this.protocol = protocol;
	}
	public int[] getState() {
		return state;
	}
	public void setState(int[] state) {
		this.state = state;
	}
	
	
	
}

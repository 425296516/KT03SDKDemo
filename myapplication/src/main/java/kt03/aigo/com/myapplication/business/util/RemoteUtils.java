package kt03.aigo.com.myapplication.business.util;

import java.util.Locale;

import android.util.Log;

import kt03.aigo.com.myapplication.business.air.AirPower;
import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Remote;

public class RemoteUtils
{
	private final static String TAG = "RemoteUtils";

	/** 获取遥控器名字 */
	public static String getRemoteName(Remote remote)
	{
		if (remote == null)
		{
			return "N/A";
		}

		Locale local = Locale.getDefault();

//		Log.w(TAG, "getRemoteName............local = " + local);
		String name = "";

		if (remote.getBrand() == null)
		{
			name += "Unknown Brand";
		}
//		else
//		{
//			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
//			{
//				if (remote.getBrand().getBrand_cn() != null && remote.getBrand().getBrand_cn().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_cn();
//				}
//				else if (remote.getBrand().getBrand_tw() != null && remote.getBrand().getBrand_tw().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_tw();
//				}
//				else if (remote.getBrand().getBrand_en() != null && remote.getBrand().getBrand_en().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_en();
//				}
//				else if (remote.getBrand().getBrand_other() != null && remote.getBrand().getBrand_other().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_other();
//				}
//				else
//				{
//					name += "Unknown Brand";
//				}
//			}
//			else
//			{
//				if (remote.getBrand().getBrand_en() != null && remote.getBrand().getBrand_en().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_en();
//				}
//				else if (remote.getBrand().getBrand_cn() != null && remote.getBrand().getBrand_cn().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_cn();
//				}
//				else if (remote.getBrand().getBrand_tw() != null && remote.getBrand().getBrand_tw().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_tw();
//				}
//				else if (remote.getBrand().getBrand_other() != null && remote.getBrand().getBrand_other().equals("") == false)
//				{
//					name += remote.getBrand().getBrand_other();
//				}
//				else
//				{
//					name += "Unknown Brand";
//				}
//			}
//		}

		switch (remote.getType())
		{
		case 1:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 电视";
			}
			else
			{
				name += " TV";
			}
			break;

		case 2:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 空调";
			}
			else
			{
				name += " AC";
			}
			break;

		case 3:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 风扇";
			}
			else
			{
				name += " fan";
			}
			break;

		case 4:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 投影仪";
			}
			else
			{
				name += " Projector";
			}
			break;

		case 5:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 机顶盒";
			}
			else
			{
				name += " STB";
			}
			break;

		case 6:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " DVD";
			}
			else
			{
				name += " DVD";
			}
			break;

		case 7:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 相机";
			}
			else
			{
				name += " Camera";
			}
			break;

		case 8:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 遥控灯";
			}
			else
			{
				name += " Light";
			}
			break;

		case 9:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 功放";
			}
			else
			{
				name += " Amplifier";
			}
			break;

		case 10:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " IPTV";
			}
			else
			{
				name += " IPTV";
			}
			break;
		case 11:
			if (local.equals(Locale.CHINA) || local.equals(Locale.CHINESE) || local.equals(Locale.SIMPLIFIED_CHINESE))
			{
				name += " 盒子";
			}
			else
			{
				name += " Box";
			}
			break;
		default:
			break;
		}
		if (remote.getModel() != null)
		{
			name += " " + remote.getModel();
		}

		return name;
	}

	/** 获取按键名称 */
	public static String getKeyName(int keyType)
	{
		String name = null;
		switch (keyType)
		{
		case KeyType.AIR_AID_HOT:
			name = "干燥";
			break;
		case KeyType.AIR_ANION:
			name = "负离子";
			break;
		case KeyType.AIR_COMFORT:
			name = "舒适";
			break;
		case KeyType.AIR_FLASH_AIR:
			name = "换气";
			break;
		case KeyType.AIR_LIGHT:
			name = "灯光";
			break;
		case KeyType.AIR_POWER_SAVING:
			name = "节能";
			break;
		case KeyType.AIR_QUICK_COOL:
			name = "一键冷";
			break;
		case KeyType.AIR_QUICK_HOT:
			name = "一键热";
			break;
		case KeyType.AIR_SLEEP:
			name = "睡眠";
			break;
		case KeyType.AIR_SUPER:
			name = "超强";
			break;
		case KeyType.AIR_TEMP_DISPLAY:
			name = "温度显示";
			break;
		case KeyType.AIR_TIME:
			name = "定时";
			break;
		case KeyType.AIR_WET:
			name = "加湿";
			break;
		case KeyType.AIR_WIND_DIRECT:
			name = "风向";
			break;
		case KeyType.AUTO:
			name = "自动";
			break;
		case KeyType.BACK:
			name = "返回";
			break;
		case KeyType.BASE_OVAL:
			name = "自定义键-椭圆";
			break;
		case KeyType.BASE_OVAL_BLUE:
			name = "自定义键-蓝色";
			break;

		case KeyType.BASE_OVAL_CYAN:
			name = "自定义键-青色";
			break;
		case KeyType.BASE_OVAL_GREEN:
			name = "自定义键-绿色";
			break;
		case KeyType.BASE_OVAL_ORANGE:
			name = "自定义键-橙色";
			break;
		case KeyType.BASE_OVAL_PURPLE:
			name = "自定义键-紫色";
			break;
		case KeyType.BASE_OVAL_RED:
			name = "自定义键-红色";
			break;
		case KeyType.BASE_OVAL_YELLOW:
			name = "自定义键-黄色";
			break;
		case KeyType.BASE_ROUND:
			name = "自定义键-圆形";
			break;
		case KeyType.BASE_SQUARE:
			name = "自定义键-方形";
			break;
		case KeyType.BOTTOM:
			name = "到底";
			break;
		case KeyType.BRIGHTNESS:
			
			name = "灯光";
			break;
		case KeyType.CHANNEL_DOWN:
			name = "频道-";
			break;
		case KeyType.CHANNEL_UP:
			name = "频道+";
			break;
		case KeyType.CONTINUE_DOWN:
			name = "连续-";
			break;
		case KeyType.CONTINUE_LEFT:
			name = "连续左";
			break;
		case KeyType.CONTINUE_RIGHT:
			name = "连续右";
			break;
		case KeyType.CONTINUE_UP:
			name = "连续+";
			break;
		case KeyType.COOL_WIND:
			name = "冷风";
			break;
		case KeyType.CUSTOM:
			name = "扩展";
			break;
		case KeyType.D_ZOOM_DOWN:
			name = "缩小";
			break;
		case KeyType.D_ZOOM_UP:
			name = "放大";
			break;
		case KeyType.DIGITAL:
			name = "数位";
			break;
		case KeyType.DUAL_SCREEN:
			name = "双画面";
			break;
		case KeyType.EIGHT:
			name = "数字键 8";
			break;
		case KeyType.FAVORITES:
			name = "收藏按钮";
			break;
		case KeyType.FIVE:
			name = "数字键 5";
			break;
		case KeyType.FORWARD:
			name = "前进";
			break;
		case KeyType.FOUR:
			name = "数字键 4";
			break;
		case KeyType.FREEZE:
			name = "画面冻结";
			break;
		case KeyType.HEAD_SHAKING:
			name = "摇头";
			break;
		case KeyType.HOME:
			name = "首页";
			break;
		case KeyType.INFORMATION:
			name = "信息显示";
			break;
		case KeyType.LANGUAGE:
			name = "语言";
			break;
		case KeyType.LOOK_BACK:
			name = "回看";
			break;
		case KeyType.MEMORYKEY:
			name = "记忆键";
			break;
		case KeyType.MEMORYKEY_ONE:
			name = "记忆键1";
			break;
		case KeyType.MEMORYKEY_TWO:
			name = "记忆键2";
			break;
		case KeyType.MENU:
			name = "菜单键";
			break;
		case KeyType.MENU_DOWN:
			name = "下翻";
			break;
		case KeyType.MENU_EXIT:
			name = "退出";
			break;
		case KeyType.MENU_LEFT:
			name = "左翻";
			break;
		case KeyType.MENU_OK:
			name = "OK键";
			break;
		case KeyType.MENU_RIGHT:
			name = "右翻";
			break;
		case KeyType.MENU_UP:
			name = "上翻";
			break;
		case KeyType.MODE:
			name = "模式";
			break;
		case KeyType.MUTE:
			name = "静音";
			break;
		case KeyType.NEXT:
			name = "下一个";
			break;
		case KeyType.NINE:
			name = "数字键 9";
			break;
		case KeyType.NUMBERS:
			name = "数字按钮";
			break;
		case KeyType.ONE:
			name = "数字键 1";
			break;
		case KeyType.OPEN:
			name = "打开";
			break;
		case KeyType.PC:
			name = "电脑";
			break;
		case KeyType.PJT_SIGNAL:
			name = "信号";
			break;
		case KeyType.PLAY_PAUSE:
			name = "暂停/播放";
			break;
		case KeyType.POP_MENU:
			name = "弹出菜单";
			break;
		case KeyType.POWER:
			name = "电源";
			break;
		case KeyType.POWER_SECOND:
			name = "第二电源键";
			break;

		case KeyType.PREVIOUS:
			name = "上一个";
			break;

		case KeyType.RESET:
			name = "重置";
			break;
		case KeyType.REWIND:
			name = "后退键";
			break;
		case KeyType.SCREEN:
			name = "屏幕";
			break;
		case KeyType.SETTING:
			name = "设置";
			break;
		case KeyType.SEVEN:
			name = "数字键 7";
			break;
		case KeyType.SHUTTER_ONE:
			name = "单反主键";
			break;
		case KeyType.SHUTTER_TWO:
			name = "单反副键";
			break;
		case KeyType.SIGNAL:
			name = "信源键";
			break;
		case KeyType.SIX:
			name = "数字键 6";
			break;
		case KeyType.SOUND_CHANNEL:
			name = "声道";
			break;
		case KeyType.STANDARD:
			name = "制式";
			break;
		case KeyType.STEP_SLOW:
			name = "慢放";
			break;
		case KeyType.STOP:
			name = "停止";
			break;
		case KeyType.SUBTITLES:
			name = "字幕";
			break;
		case KeyType.TEMP_DOWN:
			name = "温度减";
			break;
		case KeyType.TEMP_UP:
			name = "温度加";
			break;
		case KeyType.TEN_PLUS:
			name = "+10";
			break;
		case KeyType.THREE:
			name = "数字键 3";
			break;
		case KeyType.TITLE:
			name = "标题";
			break;
		case KeyType.TOP:
			name = "到顶";
			break;
		case KeyType.TOP_MENU:
			name = "顶菜单";
			break;
		case KeyType.TWO:
			name = "数字键 2";
			break;
		case KeyType.VIDEO:
			name = "视频";
			break;
		case KeyType.VOL_DOWN:
			name = "音量-";
			break;
		case KeyType.VOL_UP:
			name = "音量+";
			break;
		case KeyType.WIND_AMOUNT:
			name = "风量";
			break;
		case KeyType.WIND_CLASS:
			name = "风类";
			break;
		case KeyType.WIND_HORIZONTAL:
			name = "水平风向";
			break;
		case KeyType.WIND_VELOCITY:
			name = "风速";
			break;
		case KeyType.WIND_VERTICAL:
			name = "垂直风向";
			break;
		case KeyType.ZERO:
			name = "数字键 0";
			break;

		default:
			name = "其他";
			break;
		}
		return name;
	}

	/** 是否是态的空调遥控器 */
	public static boolean isMultiAirRemote(Remote remote)
	{
		// 缺乏数据
		if (remote == null || remote.getKeys() == null || remote.getKeys().size() == 0)
		{
			return false;
		}
		// 不是空调类型
		if (remote.getType() != ApplianceType.AIR_CONDITIONER)
		{
			return false;
		}

		// 判断是否是协议空调
		for (IRKey key : remote.getKeys())
		{
			if (key != null && key.getProtocol() > 0)
			{
				return true;
			}
		}
//		// 判断是否是DIY的组合状态空调
//		for (IRKey key : remote.getKeys())
//		{
//			if (key != null && key.getName().equalsIgnoreCase("poweroff"))
//			{
//				ETLogger.debug("is diy air");
//				return true;
//			}
//		}
		
		return false;
		
	}
	
	
	public static boolean isDiyAirRemote(Remote remote)
	{
		if (remote.getType() != ApplianceType.AIR_CONDITIONER)
		{
			return false;
		}

		// 判断是否是DIY的组合状态空调
		if(remote.getAir_keys()!=null){
			return true;
		}
		
		return false;
		
	}

	/** 是否是协议空调遥控器 */
	public static boolean isProtocolAirRemote(Remote remote)
	{
		// 缺乏数据
		if (remote == null || remote.getKeys() == null || remote.getKeys().size() == 0)
		{
			return false;
		}
		// 判断是否是协议空调
		for (IRKey key : remote.getKeys())
		{
			if (key != null && key.getProtocol() > 0)
			{
				return true;
			}
		}
		return false;
	}

	public static String getAirStrFrom(AirRemoteState state){
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
		if(state.getPower()== AirPower.POWER_OFF){
			return "POWEROFF";
		}else {
			switch (state.getMode()) {
//            case MODE_AUTO:
//                ac_text_mode.setText("自动");
//                break;
            case COOL:
               
                code2 = "C";
                break;

            case HOT:
  
                code2 = "H";
                break;
            default:
            	code2 = "C";
                break;
        }
			switch (state.getWind_amount()) {

            case LEVEL_1:
              
            	code4 = "F1";
                break;
            case LEVEL_2:
               
            	code4 = "F2";
                break;
            case LEVEL_3:
              
            	code4 = "F3";
                break;
            default:
            	code4 = "F1";
                break;
        }
			 switch (state.getTemp()) {
             case T16:
              
                 code3="16";
                 break;
             case T17:
              
                 code3="17";
                 break;
             case T18:
               
                 code3="18";
                 break;
             case T19:
                
                 code3="19";
                 break;
             case T20:
             
                 code3="20";
                 break;
             case T21:
                 
                 code3="21";
                 break;
             case T22:
                
                 code3="22";
                 break;
             case T23:
                 
                 code3="23";
                 break;
             case T24:
                
                 code3="24";
                 break;
             case T25:
                
                 code3="25";
                 break;
             case T26:
                
                 code3="26";
                 break;
             case T27:
                
                 code3="27";
                 break;
             case T28:
                
                 code3="28";
                 break;
             case T29:
               
                 code3="29";
                 break;
             case T30:
               
                 code3="30";
                 break;
             default:
            	  code3="24";
                 break;
         }
			 switch (state.getWind_direction()) {
             case AUTO:
                
                 code1 = "S";
                 break;
             case DOWN:
             case MIDDLE:
             case UP:
                 code1 = "W1";
//               
                 break;
             default:
            	 code1 = "S";
                 break;
         }
			 return  code1+code2+code3+code4;
		}
		
	}
	
	

	        

	        

	            

	             


}

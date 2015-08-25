package kt03.aigo.com.myapplication.business.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;


import com.etek.ircore.RemoteCore;

import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.KeyColumn;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.Tools;

public class IRDataBase {
	
	private static final String TAG = IRDataBase.class.getSimpleName();
	
	public static List<IRKey> getDBkeys(Context mContext,int type, int index){
		LocalDB lrdb = new LocalDB(mContext);
		List<IRKey> keys = new ArrayList<IRKey>();
		String name = "";
		lrdb.open();
		
		 ArrayList<KeyColumn> keyColumns =lrdb.getKeyColumn( type) ;
		 
		Infrared[] irs =  getDBInfrared( type,  index);
		
		for (int i=0;i<keyColumns.size();i++){
			name = lrdb.strTranslator(keyColumns.get(i).getName());

			IRKey k = new IRKey();
			k.setName(name);
			k.setId(i);
			k.setType(keyColumns.get(i).getType());
			List<Infrared> infs = new ArrayList<Infrared>();
			infs.add(irs[keyColumns.get(i).getKey_column()]);
			k.setInfrareds(infs);
			ETLogger.debug("key is " + k.getId() + "_" + k.getName() + "_" + k.getType() + "_" + k.getInfrareds().get(0).irString());
			keys.add(k);
		}
		lrdb.close();
		return keys;
		
	}
	
	public static List<IRKey> getAirkeys(Context mContext, int index){
		LocalDB lrdb = new LocalDB(mContext);
		List<IRKey> keys = new ArrayList<IRKey>();
		String name = "";
		lrdb.open();
		
		 ArrayList<KeyColumn> keyColumns =lrdb.getKeyColumn( 5) ;

		for (int i=0;i<6;i++){
			name = lrdb.strTranslator(keyColumns.get(i).getName());
            Log.d(TAG,"name="+name);
			IRKey k = new IRKey();
			k.setName(name);
			k.setId(i);
			k.setType(keyColumns.get(i).getType());
			k.setProtocol(index);
		
			ETLogger.debug("key is "+k.getId()+"_"+k.getName()+"_"+k.getType()+"_"+k.getProtocol());
			keys.add(k);
		}
		lrdb.close();
		return keys;
		
	}
	
	private static  Infrared[] getDBInfrared(int type, int index) {
		String[] dbData = RemoteCore.getDataBase(type, index);
		String irType = dbData[2];
		String custom = dbData[3];
		String data = "";
		byte[] dataList;
		ArrayList<IRCode> irLists = new ArrayList<IRCode>();
		int freq = 0;
		int[] datas = null;
		for (int i = 4; i < dbData.length; i++) {
			data = dbData[i];
			if ("".equals(data)) {
				IRCode ir = new IRCode(freq, datas);
				irLists.add(ir);
			} else {
				String dataStr = custom + data;
				// Log.d(TAG, "dataStr is  " + dataStr);
				dataList = Tools.hexStringToBytes(dataStr);
				IRCode ir = RemoteCore.prontoencode(dataList, irType);
				irLists.add(ir);
			}

		}
		Infrared[] irArray = new Infrared[irLists.size()];
		for (int i=0;i<irLists.size();i++){
			Infrared ir = new Infrared();
			ir.setInfrared(irLists.get(i));
			irArray[i] = ir;
		}
		return irArray;

	}
	public static Remote getRemote(int index) {
		// Log.v("test", "remote size is " + Globals.mRemotes.size());
		if (Globals.mRemotes != null) {
			if (Globals.mRemotes.size() > 0) {
				return Globals.mRemotes.get(index);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	public static void saveRemote(Context mContext,Remote remote) {

		
		

		
		UserDB user = new UserDB(mContext);
		user.open();
		user.saveRemote(remote);
		user.close();
	
		Globals.isAdd = true;
	}
	
	public static void getRemoteList(Context mContext) {
		UserDB user = new UserDB(mContext);
		user.open();
		ArrayList<Remote> remotes = user.getAllRemotes();
		user.close();

		Globals.mRemotes = remotes;

		
	}
}

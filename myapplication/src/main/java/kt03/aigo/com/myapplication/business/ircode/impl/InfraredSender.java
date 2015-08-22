package kt03.aigo.com.myapplication.business.ircode.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.IInfraredDevice;
import kt03.aigo.com.myapplication.business.ircode.IInfraredFetcher;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.DeviceType;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;


public class InfraredSender implements IInfraredSender {

	IInfraredFetcher mFetcher = new InfraredFetcher(RemoteApplication.getAppContext());
	IInfraredDevice mDevice = null;

	public InfraredSender(){}
	
	public InfraredSender(Context mContext) {
		super();
		int type = Globals.DEVICE;
		ETLogger.debug("type is " + type);
		switch (type) {
		
	/*	case DeviceType.ET4003:
			mDevice = new ET4003IRDevice();
			break;
		case DeviceType.ET4007:
			ETLogger.debug("4007irdevice");
			mDevice = new ET4007IRDevice();
			break;*/
		default:
			ETLogger.debug("dummyirdevice");
			mDevice = new DummyIRDevice(mContext);
			break;

		}
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public List<Infrared> send(Remote remote, IRKey key) {
		// TODO Auto-generated method stub
		
		if (remote == null || remote.getId() == null || key == null) {
			ETLogger.error("remote is null ");
			return null;
		}
		
		List<Infrared> infrareds = null;
		ETLogger.debug("remote.getType() "+remote.getType());
		if (remote.getType() == ApplianceType.AIR_CONDITIONER) {
			ETLogger.debug("is air conditioner ");
			if (RemoteUtils.isMultiAirRemote(remote)) {

				AirRemoteState state = AirRemoteStateMananger.getInstance(
						RemoteApplication.getAppContext()).getAirRemoteState(
						remote.getId());
				
				infrareds = mFetcher.fetchAirInfrareds(remote, key, state);

			}else if (RemoteUtils.isDiyAirRemote(remote)) {

				AirRemoteState state = AirRemoteStateMananger.getInstance(
                        RemoteApplication.getAppContext()).getAirRemoteState(
						remote.getId());
				
				infrareds = mFetcher.fetchAirInfrareds(remote, key, state);
//				ETLogger.debug(ETJSON.toJSONString(infrareds));
			}  else  {

				infrareds = mFetcher.fetchInfrareds(remote, key);
			}
		} else {
//			Logger.debug("remote is OK ");
			infrareds = mFetcher.fetchInfrareds(remote, key);
		}
		
		List<Infrared> list = new ArrayList<Infrared>();
		
		if (infrareds != null) {
//			Logger.debug("infrareds is  "+infrareds.get(0).irString());
			for (Infrared ir : infrareds) {
//				Logger.debug("infrareds is  "+ir.irString());
				if (ir != null && ir.getSignal() != null&&ir.isValid()) {
//					Logger.debug("infrareds is  "+infrareds.get(0).irString());
					mDevice.sendIr(ir.getFreq(), ir.getSignal());
					
					Infrared infrared = new Infrared();
					infrared.setFreq(ir.getFreq());
					infrared.setSignal(ir.getSignal());
					
					list.add(infrared);
				}
			}
		}
		return list;
	}
}

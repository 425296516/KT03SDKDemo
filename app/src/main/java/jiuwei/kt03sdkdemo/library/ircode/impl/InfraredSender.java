package jiuwei.kt03sdkdemo.library.ircode.impl;


import java.util.ArrayList;
import java.util.List;

import jiuwei.kt03sdkdemo.library.air.AirRemoteState;
import jiuwei.kt03sdkdemo.library.bean.IRKey;
import jiuwei.kt03sdkdemo.library.bean.Infrared;
import jiuwei.kt03sdkdemo.library.bean.Remote;
import jiuwei.kt03sdkdemo.library.ircode.AirRemoteStateMananger;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredDevice;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredFetcher;
import jiuwei.kt03sdkdemo.library.ircode.IInfraredSender;
import jiuwei.kt03sdkdemo.library.util.ApplianceType;
import jiuwei.kt03sdkdemo.library.util.ETLogger;
import jiuwei.kt03sdkdemo.library.util.Globals;
import jiuwei.kt03sdkdemo.library.util.RemoteApplication;
import jiuwei.kt03sdkdemo.library.util.RemoteUtils;

public class InfraredSender implements IInfraredSender {

    IInfraredFetcher mFetcher = new InfraredFetcher(
            RemoteApplication.getAppContext());
    IInfraredDevice mDevice = new DummyIRDevice();

    public InfraredSender() {
        super();
        int type = Globals.DEVICE;
        ETLogger.debug("type is " + type);
        switch (type) {

            default:
                ETLogger.debug("dummyirdevice");
                mDevice = new DummyIRDevice();
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
        ETLogger.debug("remote.getType() " + remote.getType());
        if (remote.getType() == ApplianceType.AIR_CONDITIONER) {
            ETLogger.debug("is air conditioner ");
            if (RemoteUtils.isMultiAirRemote(remote)) {

                AirRemoteState state = AirRemoteStateMananger.getInstance(
                        RemoteApplication.getAppContext()).getAirRemoteState(
                        remote.getId());

                infrareds = mFetcher.fetchAirInfrareds(remote, key, state);

            } else if (RemoteUtils.isDiyAirRemote(remote)) {

                AirRemoteState state = AirRemoteStateMananger.getInstance(
                        RemoteApplication.getAppContext()).getAirRemoteState(
                        remote.getId());

                infrareds = mFetcher.fetchAirInfrareds(remote, key, state);
//				ETLogger.debug(ETJSON.toJSONString(infrareds));
            } else {

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
                if (ir != null && ir.getSignal() != null && ir.isValid()) {
//					Logger.debug("infrareds is  "+infrareds.get(0).irString());
                    //mDevice.sendIr(ir.getFreq(), ir.getSignal());

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

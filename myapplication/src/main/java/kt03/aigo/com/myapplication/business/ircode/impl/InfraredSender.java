package kt03.aigo.com.myapplication.business.ircode.impl;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.air.AirRemoteState;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.AirRemoteStateMananger;
import kt03.aigo.com.myapplication.business.ircode.IInfraredDevice;
import kt03.aigo.com.myapplication.business.ircode.IInfraredFetcher;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.business.util.RemoteUtils;

public class InfraredSender implements IInfraredSender {

    IInfraredFetcher mFetcher = new InfraredFetcher(
            RemoteApplication.getAppContext());
    //IInfraredDevice mDevice = new DummyIRDevice();

    public InfraredSender() {
        super();
        int type = Globals.DEVICE;
        Log.d(TAG, "type is " + type);
        switch (type) {

            default:
                Log.d(TAG, "dummyirdevice");
                //mDevice = new DummyIRDevice();
                break;
        }
    }

    @Override
    public List<Infrared> send(Remote remote, IRKey key) {
        if (remote == null || remote.getId() == null || key == null) {
            Log.d(TAG, "remote is null ");
            return null;
        }

        List<Infrared> infrareds = null;
        Log.d(TAG, "remote.getType() " + remote.getType());
        if (remote.getType() == Globals.AIR_CONDITIONER) {
            Log.d(TAG, "is air conditioner ");
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
            }
        }

        List<Infrared> list = new ArrayList<Infrared>();

        if (infrareds != null) {
            for (Infrared ir : infrareds) {
                if (ir != null && ir.getSignal() != null && ir.isValid()) {

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

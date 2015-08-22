package kt03.aigo.com.myapplication.business.bean;

import java.io.Serializable;
import java.util.List;










import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.ircode.impl.InfraredSender;
import kt03.aigo.com.myapplication.business.util.ApplianceType;
import kt03.aigo.com.myapplication.business.util.ETLogger;
import kt03.aigo.com.myapplication.ui.ui.AutoModelActivity;

public class IRKeyAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mInflater;
	Remote mRemote;

	IInfraredSender mSeneder;
	CallbackOnInfraredSended mCallbackOnInfraredSended;

	public IRKeyAdapter(Context context, Remote remote,
			CallbackOnInfraredSended callback) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mRemote = remote;
		mCallbackOnInfraredSended = callback;
		//ETLogger.debug("keyadapt init");
		mSeneder = new InfraredSender(mContext);
	}

	public IRKeyAdapter(Context context, Remote remote) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mRemote = remote;

		//ETLogger.debug("keyadapt init");
		mSeneder = new InfraredSender(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mRemote != null && mRemote.getKeys() != null) {
			return mRemote.getKeys().size();
		}
		return 0;
	}

	@Override
	public IRKey getItem(int position) {
		// TODO Auto-generated method stub
		if (mRemote != null && mRemote.getKeys() != null) {
			return mRemote.getKeys().get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		final int p = position;
		if (convertView == null)// 初始化一条item
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.grid_item_key, parent,
					false);
			holder.txt_key_name = (TextView) convertView
					.findViewById(R.id.key_name);
			holder.txt_infrared = (TextView) convertView
					.findViewById(R.id.key_signal);
			holder.btn_send = (Button) convertView
					.findViewById(R.id.key_button);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final IRKey key = mRemote.getKeys().get(position);

		if (key != null) {

			holder.txt_key_name.setText(key.getName());
			holder.btn_send.setText(key.getName());
			if (mRemote.getType() != ApplianceType.AIR_CONDITIONER) {
				if (key.getInfrareds() != null || key.getInfrareds().size() > 0) {
					// holder.txt_infrared.setText(String.valueOf(key.getInfrareds()
					// == null ? 0 : key.getInfrareds().size()) + " 个信号");
					holder.txt_infrared.setText(key.getInfrareds().get(0)
							.irString());
				} else {
					holder.txt_infrared.setText("");
				}
//				if(RemoteUtils.isDiyAirRemote(mRemote)){
//					String name = getAirKeyNameDesc(key.getName());
//					holder.txt_key_name.setText(key.getName());
//				}
			}
			// holder.txt_key_name.setVisibility(View.GONE);
		} else {
			holder.txt_key_name.setText("未知按键");
		}

		holder.btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Log.v("DEBUG", key.getInfrareds().get(0).irString());
				// Logger.debug(key.getInfrareds().get(0).irString());
				List<Infrared> list = mSeneder.send(mRemote, key);
				if(list!=null){
				Intent intent = new Intent(mContext,AutoModelActivity.class);
				intent.putExtra("INFRARED", (Serializable)list);
				
				//if (key.getInfrareds() != null || key.getInfrareds().size() > 0) {
				//intent.putExtra("keyCode", "::"+key.getInfrareds().get(0).getFreq());
				//}else{
					//intent.putExtra("keyCode", "::");
				//}
				mContext.startActivity(intent);
				}
				
				mCallbackOnInfraredSended.onInfrardSended();
				
			}
		});
		holder.btn_send.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				mCallbackOnInfraredSended.onLongPress(p);
				ETLogger.debug("longpress");
				return false;
			}
		});
		return convertView;
	}



//	private  String  getAirKeyNameDesc(String name) {
//		// TODO Auto-generated method stub
//		if(name.)
//		String desc = null;
//		return desc;
//	}



	public final class ViewHolder {
		public TextView txt_key_name;
		public TextView txt_infrared;
		public Button btn_send;
	}

}
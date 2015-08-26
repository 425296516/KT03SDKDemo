package kt03.aigo.com.myapplication.business.adapter;

import java.util.List;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.util.Globals;

public class RemoteAdapter extends BaseAdapter {
	protected static final String TAG = RemoteAdapter.class.getSimpleName();
	private Context mContext;
	private List<Remote> mRemoteList;

	public RemoteAdapter(Context context, List<Remote> lists) {
		this.mContext = context;
		this.mRemoteList = lists;
	}

	@Override
	public int getCount() {
		if (mRemoteList != null) {
			return mRemoteList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return mRemoteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		Remote rmt = mRemoteList.get(position);
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.list_remote_item,null);
		}

		holder = new Holder();
		holder.iv = (ImageView) convertView.findViewById(R.id.remote_pic);
		holder.tv_name = (TextView) convertView.findViewById(R.id.remote_name);
		holder.tv_desc = (TextView) convertView
				.findViewById(R.id.remote_description);
		holder.tv_model = (TextView) convertView.findViewById(R.id.remote_time);
		convertView.setTag(holder);
		int type = rmt.getType();

		int bm = Globals.getImgID(type);

		holder.iv.setImageResource(bm);
		holder.tv_name.setText(rmt.getName());
		holder.tv_desc.setText(rmt.getModel());
		holder.tv_model.setText("NUM " + position);

		return convertView;
	}

	class Holder {
		ImageView iv;
		TextView tv_name;
		TextView tv_desc;
		TextView tv_model;
	}

	public void clearRemotes() {
		mRemoteList.clear();
	}

	public void showRemotes(List<Remote> remotes) {
		if (remotes == null) {
			return;
		}

		mRemoteList = remotes;
		notifyDataSetChanged();
	}
}

package kt03.aigo.com.myapplication.business.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.ircode.CallbackOnInfraredSended;
import kt03.aigo.com.myapplication.business.ircode.IInfraredSender;
import kt03.aigo.com.myapplication.business.ircode.impl.InfraredSender;
import kt03.aigo.com.myapplication.business.util.Constant;

public class IRKeyAdapter extends BaseAdapter {
    private static final String TAG = IRKeyAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mInflater;
    private Remote mRemote;
    private IInfraredSender mSeneder;
    private CallbackOnInfraredSended mCallbackOnInfraredSended;

    public IRKeyAdapter(Context context, Remote remote, CallbackOnInfraredSended callback) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mRemote = remote;
        mCallbackOnInfraredSended = callback;
        Log.d(TAG, "keyadapt init");
        mSeneder = new InfraredSender();
    }

    public IRKeyAdapter(Context context, Remote remote) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mRemote = remote;

        Log.d(TAG, "keyadapt init");
        mSeneder = new InfraredSender();
    }

    @Override
    public int getCount() {
        if (mRemote != null && mRemote.getKeys() != null) {
            return mRemote.getKeys().size();
        }
        return 0;
    }

    @Override
    public IRKey getItem(int position) {

        if (mRemote != null && mRemote.getKeys() != null) {
            return mRemote.getKeys().get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //final int p = position;
        if (convertView == null)// 初始化一条item
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_item_key, parent, false);
            holder.txt_key_name = (TextView) convertView.findViewById(R.id.key_name);
            holder.txt_infrared = (TextView) convertView.findViewById(R.id.key_signal);
            holder.btn_send = (Button) convertView.findViewById(R.id.key_button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final IRKey key = mRemote.getKeys().get(position);

        Log.d(TAG, key.toString());
        if (key != null) {
            holder.txt_key_name.setText(key.getName());
            holder.btn_send.setText(key.getName());
            if (key.getInfrareds() != null && key.getInfrareds().size() > 0) {

                holder.txt_infrared.setText(key.getInfrareds().get(0).irString());
            } else {
                holder.txt_infrared.setText("");
            }

        } else {
            holder.txt_key_name.setText("未知按键");
        }

        holder.btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<Infrared> list = mSeneder.send(mRemote, key);
                Constant.infraredList = list;

                mCallbackOnInfraredSended.onInfrardSended();

            }
        });
        holder.btn_send.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                mCallbackOnInfraredSended.onLongPress(position);
                Log.d(TAG, "longpress");
                return false;
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView txt_key_name;
        public TextView txt_infrared;
        public Button btn_send;
    }
}
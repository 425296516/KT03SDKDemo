package kt03.aigo.com.myapplication.business.db;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.KeyColumn;

public class IRDataBase {

    private static final String TAG = IRDataBase.class.getSimpleName();

    public static List<IRKey> getAirkeys(Context mContext, int index) {
        LocalDB lrdb = new LocalDB(mContext);
        List<IRKey> keys = new ArrayList<IRKey>();
        String name = "";
        lrdb.open();

        ArrayList<KeyColumn> keyColumns = lrdb.getKeyColumn(5);

        for (int i = 0; i < 6; i++) {
            name = lrdb.strTranslator(keyColumns.get(i).getName());
            Log.d(TAG, "name=" + name);
            IRKey k = new IRKey();
            k.setName(name);
            k.setId(i);
            k.setType(keyColumns.get(i).getType());
            k.setProtocol(index);

            Log.d(TAG, "key is " + k.getId() + "_" + k.getName() + "_" + k.getType() + "_" + k.getProtocol());
            keys.add(k);
        }
        lrdb.close();
        return keys;
    }
}
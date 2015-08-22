package kt03.aigo.com.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.IRCode;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Infrared;
import kt03.aigo.com.myapplication.business.bean.ModelNum;
import kt03.aigo.com.myapplication.business.bean.SortAdapter;
import kt03.aigo.com.myapplication.business.util.Constant;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.HttpRequest;
import kt03.aigo.com.myapplication.ui.ui.CharacterParser;
import kt03.aigo.com.myapplication.ui.ui.MatchRemote;
import kt03.aigo.com.myapplication.ui.ui.ModelPinyinComparator;
import kt03.aigo.com.myapplication.ui.ui.SideBar;
import kt03.aigo.com.myapplication.ui.ui.SortModel;

public class BrandListActivity extends ActionBarActivity {

    protected static final int GET_MODEL_NUM_OK = 100;
    private ListView brandListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    String TAG = "LocalBrandListActivity";
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> nameList = new ArrayList<String>();
    private int mType;
    private String mTypeName = null;

//	private final String getModelNum = "http://222.191.229.234:10068/PhoneRemoteServer/wyf/getmodelnumber";

    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    private ModelPinyinComparator pinyinComparator;
    public static BrandListActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);


        initViews();
    }

    private Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MODEL_NUM_OK:

                    Globals.modelSearchs = ((List<ModelNum>) msg.obj);
                    if(Globals.modelSearchs.size()>0&&Globals.modelSearchs!=null){
                        Intent intent = new Intent(BrandListActivity.this,
                                MatchRemote.class);

                        startActivity(intent);
                    }else{
                        Toast.makeText(instance, "no model selection", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    };


    private void initViews() {

        characterParser = CharacterParser.getInstance();

        pinyinComparator = new ModelPinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);

        sideBar.setTextView(dialog);
      /*  int typeId = Globals.getTypeStrID(Globals.deviceID);
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
                View.GONE);
        mTitleBarView.setTitleText(typeId);
        mTitleBarView.setBtnLeft(R.string.device_type);*/
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    brandListView.setSelection(position);
                }

            }
        });

        brandListView = (ListView) findViewById(R.id.brand_list);
        brandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                    Brand mBrand = new Brand();
                    String brandName = ((SortModel) adapter.getItem(position))
                            .getName();
                    mBrand.setBrand_tra(brandName);
                    int brandId = getBrandId(brandName);
              /*    Log.d("device is " + Globals.deviceID
                          + "  brandid is " + brandId);*/
                    String name = getBrandName(brandName);
                    mBrand.setBrand(name);
                    mBrand.setSortLetters(name);
                    mBrand.setId(brandId);
                Globals.MBrand = mBrand;
                    Thread thread = new Thread(new GetModelNumRunnable(
                            5 , brandId));
                    thread.start();

            }
        });

        getBrand(mTypeName);
        String[] nameListSTr = new String[nameList.size()];
        nameList.toArray(nameListSTr);

        SourceDateList = filledData(nameListSTr);

        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        brandListView.setAdapter(adapter);

        // getWindow().setSoftInputMode(
        // WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        // | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }


    int getBrandId(String brand_tr) {
        for (Brand b : Globals.MBrands) {
            if (b.getBrand_tra().equalsIgnoreCase(brand_tr)) {
                //ETLogger.debug("getBrand is " + brand_tr);
                return b.getId();
            }
        }
        return 0;
    }

    String getBrandName(String brand_tr) {
        for (Brand b : Globals.MBrands) {
            if (b.getBrand_tra().equalsIgnoreCase(brand_tr)) {

                return b.getBrand();
            }
        }
        return null;
    }

    private ArrayList<String> getBrand(String _type) {

        for (Brand b : Globals.MBrands) {
            nameList.add(b.getBrand_tra());
        }
        return nameList;


    }

    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);

            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }


    class GetModelNumRunnable implements Runnable {

        private Integer idDevice;
        private Integer idBrand;

        GetModelNumRunnable(Integer idDevice, Integer idBrand) {
            this.idDevice = idDevice;
            this.idBrand = idBrand;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            // String str;

            List<ModelNum> modelSearch = new ArrayList<ModelNum>();


            Gson gson = new Gson();
            // String resp = HttpRequest.sendGet(getModelNum + "/" +
            // idDevice.toString() + "/" + idBrand.toString());
            // Logger.debug(resp);
            String cmd = Constant.GETSERVERSEARCHREMOTE
                    + idDevice.toString() + "/"
                    + idBrand.toString();
            //ETLogger.debug(cmd);
            Map<Integer, List<Object[]>> searchRemoteKeys = gson.fromJson(HttpRequest.sendGet(cmd), new TypeToken<Map<Integer, List<Object[]>>>() {
            }.getType());

            Iterator<Map.Entry<Integer, List<Object[]>>> it = searchRemoteKeys.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, List<Object[]>> entry = it.next();
                ModelNum ms = new ModelNum(entry.getKey());
                List<Object[]> values = entry.getValue();
                List<IRKey> irkeys = new ArrayList<IRKey>();
                for (int i = 0; i < values.size(); i++) {
                    Object[] obj = values.get(i);
                    IRKey irkey = new IRKey();
                    irkey.setName((String) obj[0]);
                    IRCode ir = new IRCode((String) obj[1]);
                    Infrared inf = new Infrared(ir);
                    List<Infrared> infrareds = new ArrayList<Infrared>();
                    infrareds.add(inf);
                    irkey.setInfrareds(infrareds);
                    irkeys.add(irkey);
                }
                ms.setKeys(irkeys);
                modelSearch.add(ms);

            }


            Message message = new Message();
            message.what = GET_MODEL_NUM_OK;
            message.obj = modelSearch;
            handler.sendMessage(message);
        }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_brand_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

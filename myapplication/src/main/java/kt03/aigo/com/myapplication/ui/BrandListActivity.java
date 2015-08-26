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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.bean.ModelNum;
import kt03.aigo.com.myapplication.business.bean.ModelNumObject;
import kt03.aigo.com.myapplication.business.db.LocalDB;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;
import kt03.aigo.com.myapplication.ui.pinyin.CharacterParser;
import kt03.aigo.com.myapplication.ui.pinyin.ModelPinyinComparator;
import kt03.aigo.com.myapplication.ui.pinyin.SideBar;
import kt03.aigo.com.myapplication.ui.pinyin.SortAdapter;
import kt03.aigo.com.myapplication.ui.pinyin.SortModel;

public class BrandListActivity extends ActionBarActivity {

    private static final String TAG = BrandListActivity.class.getSimpleName();
    protected static final int GET_MODEL_NUM_OK = 100;
    private ListView brandListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> nameList = new ArrayList<String>();
    private int mType;
    private String mTypeName = null;
    private LocalDB mRmtDB;

//	private final String getModelNum = "http://222.191.229.234:10068/PhoneRemoteServer/wyf/getmodelnumber";

    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    private ModelPinyinComparator pinyinComparator;
    public static BrandListActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);

        initData();
        initViews();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MODEL_NUM_OK:

                    Globals.modelSearchs = ((List<ModelNum>) msg.obj);
                    if (Globals.modelSearchs != null && Globals.modelSearchs.size() > 0) {
                        Intent intent = new Intent(BrandListActivity.this,
                                MatchRemote.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(instance, "no model selection", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private void initData() {
        mRmtDB = new LocalDB(getApplicationContext());

        mType = Globals.deviceID;
        mTypeName = Globals.getTypeStr(mType);
        instance = this;
        RemoteApplication.getInstance().addActivity(instance);
    }

    private void initViews() {

        characterParser = CharacterParser.getInstance();

        pinyinComparator = new ModelPinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);

        sideBar.setTextView(dialog);

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
                                    final int position, long id) {

                Brand mBrand = new Brand();
                final String brandName = ((SortModel) adapter.getItem(position)).getName();
                mBrand.setBrand_tra(brandName);
                final int brandId = getBrandId(brandName);
                String name = getBrandName(brandName);
                mBrand.setBrand(name);
                mBrand.setSortLetters(name);
                mBrand.setId(brandId);
                Globals.MBrand = mBrand;

                Module.getInstance().getModelNumList(brandId, new Module.OnPostListener<ModelNumObject>() {
                    @Override
                    public void onSuccess(ModelNumObject result) {
                        Log.d(TAG, result.getModelNumList().toString());
                        //ToastUtil.showToast(getApplicationContext(),brandId+brandName);
                        if (result != null && result.getModelNumList().size() > 0) {
                            Message message = new Message();
                            message.what = GET_MODEL_NUM_OK;
                            message.obj = result.getModelNumList();
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });


            }
        });

        getBrand(mTypeName);
        String[] nameListSTr = new String[nameList.size()];
        nameList.toArray(nameListSTr);

        SourceDateList = filledData(nameListSTr);

        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        brandListView.setAdapter(adapter);

    }


    int getBrandId(String brand_tr) {
        for (Brand b : Globals.MBrands) {
            if (b.getBrand_tra().equalsIgnoreCase(brand_tr)) {

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

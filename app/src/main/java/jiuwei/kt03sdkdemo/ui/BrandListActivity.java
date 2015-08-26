package jiuwei.kt03sdkdemo.ui;

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

import jiuwei.kt03sdkdemo.R;
import jiuwei.kt03sdkdemo.library.Module;
import jiuwei.kt03sdkdemo.library.bean.Brand;
import jiuwei.kt03sdkdemo.library.bean.ModelNum;
import jiuwei.kt03sdkdemo.library.bean.SortAdapter;
import jiuwei.kt03sdkdemo.library.db.LocalDB;
import jiuwei.kt03sdkdemo.library.task.ModelNumObject;
import jiuwei.kt03sdkdemo.library.util.Globals;
import jiuwei.kt03sdkdemo.library.util.RemoteApplication;
import jiuwei.kt03sdkdemo.ui.ui.CharacterParser;
import jiuwei.kt03sdkdemo.ui.ui.MatchRemote;
import jiuwei.kt03sdkdemo.ui.ui.ModelPinyinComparator;
import jiuwei.kt03sdkdemo.ui.ui.SideBar;
import jiuwei.kt03sdkdemo.ui.ui.SortModel;


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

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MODEL_NUM_OK:

                    Globals.modelSearchs = ((List<ModelNum>) msg.obj);
                    if (Globals.modelSearchs.size() > 0 && Globals.modelSearchs != null) {
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
                final String brandName = ((SortModel) adapter.getItem(position))
                        .getName();
                mBrand.setBrand_tra(brandName);
                final int brandId = getBrandId(brandName);
              /*    Log.d("device is " + Globals.deviceID
                          + "  brandid is " + brandId);*/
                String name = getBrandName(brandName);
                mBrand.setBrand(name);
                mBrand.setSortLetters(name);
                mBrand.setId(brandId);
                Globals.MBrand = mBrand;
                    /*Thread thread = new Thread(new GetModelNumRunnable(
                            5 , brandId));
                    thread.start();*/

                Module.getInstance().getModelNumList(brandId,new Module.OnPostListener<ModelNumObject>() {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_brand_list, menu);
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

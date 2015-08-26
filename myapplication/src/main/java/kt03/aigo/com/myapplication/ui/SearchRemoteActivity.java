package kt03.aigo.com.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.bean.IRKey;
import kt03.aigo.com.myapplication.business.bean.Remote;
import kt03.aigo.com.myapplication.business.adapter.RemoteAdapter;
import kt03.aigo.com.myapplication.business.bean.IRKeyList;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.RemoteApplication;

public class SearchRemoteActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final static String TAG = SearchRemoteActivity.class.getSimpleName();
    public final static int GET_SEARCH_MODEL_OK = 100;
    public final static int GET_IR_KEY_OK = 101;
    public final static int GET_IR_KEY_FAIL = 102;

    private static Context mContext;
    private EditText edKeyword; // 关键词输入
    private Button btnSearch; // 搜索按钮
    public static SearchRemoteActivity instance;

    private ListView listview_remotes;// 展示遥控器
    private RemoteAdapter mRemoteAdapter;

    private Remote mRemote;

    private List<Remote> mRemotes;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        // Intent intent = new Intent();

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_SEARCH_MODEL_OK:
                    displayRemotes(mRemotes);
                    break;
                case GET_IR_KEY_OK:
                    Globals.mRemote = mRemote;
                    Intent i = new Intent(mContext, ConfirmRemoteActivity.class);
                    startActivity(i);
                    break;
                case GET_IR_KEY_FAIL:
                    Toast.makeText(mContext, "no irkey return error", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        instance = this;
        RemoteApplication.getInstance().addActivity(instance);
        createView();
    }

    public void createView() {

        setContentView(R.layout.activity_search_remote);
        mRemotes = new ArrayList<Remote>();
        edKeyword = (EditText) findViewById(R.id.edit_keyword);
        btnSearch = (Button) findViewById(R.id.btn_search);
        listview_remotes = (ListView) findViewById(R.id.listview_remotes);

        mRemoteAdapter = new RemoteAdapter(mContext, mRemotes);

        listview_remotes.setAdapter(mRemoteAdapter);
        listview_remotes.setOnItemClickListener(this);

        btnSearch.setOnClickListener(this);


        edKeyword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 重置搜索
                resetSearch();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // search(this);

    }

    /**
     * 重置搜索
     */
    void resetSearch() {

        mRemoteAdapter.clearRemotes();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_search:
                //Thread thread = new Thread(new GetKeywordRunnable(edKeyword.getText().toString()));
                //thread.start();

                Module.getInstance().getKeyWord(edKeyword.getText().toString().trim(), new Module.OnPostListener<List<Remote>>() {
                    @Override
                    public void onSuccess(List<Remote> result) {
                        mRemotes = result;
                        Message message = new Message();
                        message.what = GET_SEARCH_MODEL_OK;

                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
                break;

            default:
                break;
        }
    }

    /**
     * 展示搜索到的遥控器
     */
    private void displayRemotes(List<Remote> remotes) {
        if (remotes == null || remotes.size() == 0) {
            return;
        }

        // 在listview中展示
        mRemoteAdapter.showRemotes(remotes);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        mRemote = (Remote) mRemoteAdapter.getItem(position);
        Log.d(TAG, "remote=" + mRemote.toString());

        Module.getInstance().getRemoteKey(Integer.parseInt(mRemote.getId()), new Module.OnPostListener<IRKeyList>() {
            @Override
            public void onSuccess(IRKeyList result) {
                List<IRKey> irKeys = result.getIrKeys();

                if (irKeys != null && irKeys.size() > 0) {

                    Message message = new Message();
                    message.what = GET_IR_KEY_OK;
                    mRemote.setKeys(irKeys);
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = GET_IR_KEY_FAIL;

                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String err) {

            }
        });

        //Thread thread = new Thread(new GetRemoteRunnable(mRemote.getId()));
        //thread.start();
//		Globals.mIndex= position;
//		Intent i = new Intent(SearchRemote.this, ConfirmRemote.class);
//		startActivity(i);

    }
}



/*
*
*


class GetKeywordRunnable implements Runnable {

    private String keyWords;

    GetKeywordRunnable(String keyWord) {
        this.keyWords = keyWord;
//			this.keyWords = "45";
    }

    @SuppressLint("UseValueOf")
    @Override
    public void run() {

        mRemote = new Remote();
        Gson gson = new Gson();
        String getCmdBase64 = Base64.encodeToString(keyWords.getBytes(), Base64.DEFAULT);

//			ETLogger.debug(Globals.GETSERVERKEYWOARDS + keyWords);
        // ETLogger.debug(getValue);
        String url = Globals.GETSERVERKEYWOARDS + getCmdBase64;
        Log.d(TAG,"keyword="+url);
        try {
            Object[][] searchRemote = gson.fromJson(
                    HttpRequest.sendGet(url), new TypeToken<Object[][]>() {
                    }.getType());
            for (Object[] object : searchRemote) {
                Remote remote = new Remote();
                Double temp = (Double) object[0];
                int tempInt = (new Double(temp)).intValue();
                remote.setId(String.valueOf(tempInt));
                temp = (Double) object[1];
                tempInt = (new Double(temp)).intValue();
                remote.setType(tempInt);
                Brand brand = new Brand();
                brand.setBrand((String) object[2]);
                brand.setBrand_tra((String) object[3]);
                brand.setSortLetters((String) object[2]);
                remote.setBrand(brand);
                remote.setModel((String) object[4]);
                remote.setName((String) object[4]);
                ETLogger.info(TAG, new Gson().toJson(remote));
                mRemotes.add(remote);
            }

            Message message = new Message();
            message.what = GET_SEARCH_MODEL_OK;

            handler.sendMessage(message);
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}


class GetRemoteRunnable implements Runnable {

    private String keyWords;

    GetRemoteRunnable(String keyWord) {
        // this.keyWords = keyWord;
        this.keyWords = keyWord;
    }

    @SuppressLint("UseValueOf")
    @Override
    public void run() {
        // TODO Auto-generated method stub

        List<IRKey> irKeys = new ArrayList<IRKey>();
        Gson gson = new Gson();

        String remoteKeyId = Globals.GETSERVERREMOTEKEY
                + keyWords;
//			ETLogger.debug(remoteKeyId);
//			String  date = HttpRequest.sendGet(remoteKeyId);
//			ETLogger.debug(date);
        Object[][] comIrKeys = gson.fromJson(
                HttpRequest.sendGet(remoteKeyId),
                new TypeToken<Object[][]>() {
                }.getType());

        for (Object[] object : comIrKeys) {
            IRKey irKey = new IRKey();
            irKey.setName((String) object[0]);
//				ETLogger.debug((String) object[1]);
            List<Infrared> infs = new ArrayList<Infrared>();
            IRCode ir = new IRCode((String) object[1]);
            Infrared inf = new Infrared(ir);

            infs.add(inf);

            ir = new IRCode((String) object[2]);
            inf = new Infrared(ir);
            infs.add(inf);
            irKey.setProtocol(0);
            irKey.setInfrareds(infs);
            // irKey.setDescription((String) object[5]);
            irKeys.add(irKey);
        }
        if (irKeys != null && irKeys.size() > 0) {

            Message message = new Message();
            message.what = GET_IR_KEY_OK;
            mRemote.setKeys(irKeys);
            handler.sendMessage(message);
        } else {
            Message message = new Message();
            message.what = GET_IR_KEY_FAIL;

            handler.sendMessage(message);
        }
    }

}
*
* */
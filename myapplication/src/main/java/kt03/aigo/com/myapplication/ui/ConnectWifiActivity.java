package kt03.aigo.com.myapplication.ui;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.demo_activity.EspWifiAdminSimple;
import com.espressif.iot.esptouch.task.__IEsptouchTask;

import kt03.aigo.com.myapplication.R;


public class ConnectWifiActivity extends Activity {

    private static final String TAG = ConnectWifiActivity.class.getSimpleName();
    private Spinner mETWiFiName;
    private EditText mETWiFiPassword;
    private Button mNext;
    private EspWifiAdminSimple mWifiAdmin;
   // private Spinner mSpinnerTaskCount;
   private WifiManager wifiManager;
   private List<ScanResult> list;
    //List<String> listSSID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_wifi);

        mWifiAdmin = new EspWifiAdminSimple(this);
        mETWiFiName = (Spinner)findViewById(R.id.et_wifi_name_list);
        mETWiFiPassword = (EditText)findViewById(R.id.et_wifi_password_list);
        mNext = (Button)findViewById(R.id.btn_wifi_next);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        list = wifiManager.getScanResults();

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apSsid = (String)mETWiFiName.getItemAtPosition(mETWiFiName.getSelectedItemPosition());
                //String apSsid = list.get(mETWiFiName.getSelectedItemPosition()).SSID;
                //String apSsid = Integer.toString(mETWiFiName .getSelectedItemPosition());
                String apPassword = mETWiFiPassword.getText().toString();
                String apBssid = mWifiAdmin.getWifiConnectedBssid();
                Boolean isSsidHidden = false;
                String isSsidHiddenStr = "FALSE";

                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                            + ", " + " mEdtApPassword = " + apPassword);
                }
                new EsptouchAsyncTask().execute(apSsid, apBssid, apPassword,
                        isSsidHiddenStr);
            }
        });

        initSpinner();

    }

    private void initSpinner()
    {


        String[] spinnerItemsInteger = new String[list.size()];
        for(int i=0;i<list.size();i++)
        {
            spinnerItemsInteger[i] = list.get(i).SSID;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, spinnerItemsInteger);
        mETWiFiName.setAdapter(adapter);
        mETWiFiName.setSelection(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            //mETWiFiName
        } else {
            //mETWiFiName.setText("");
        }
        // check whether the wifi is connected
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        mNext.setEnabled(!isApSsidEmpty);
    }
    private class EsptouchAsyncTask extends AsyncTask<String, Void, IEsptouchResult> {

        private ProgressDialog mProgressDialog;

        private IEsptouchTask mEsptouchTask;
        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(ConnectWifiActivity.this);
            mProgressDialog
                    .setMessage("Esptouch is configuring, please wait for a moment...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    synchronized (mLock) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "progress dialog is canceled");
                        }
                        if (mEsptouchTask != null) {
                            mEsptouchTask.interrupt();
                        }
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Waiting...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(false);
        }

        @Override
        protected IEsptouchResult doInBackground(String... params) {
            synchronized (mLock) {
                String apSsid = params[0];
                String apBssid = params[1];
                String apPassword = params[2];
                String isSsidHiddenStr = params[3];
                boolean isSsidHidden = false;

                Log.d(TAG,"apSsid="+apSsid+"apBssid="+apBssid+"apPassword="+apPassword+"isSsidHiddenStr="+isSsidHiddenStr+"isSsidHidden="+isSsidHidden);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,
                        isSsidHidden, ConnectWifiActivity.this);
            }
            IEsptouchResult result = mEsptouchTask.executeForResult();
            return result;
        }

        @Override
        protected void onPostExecute(IEsptouchResult result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "Confirm");
            // it is unnecessary at the moment, add here just to show how to use isCancelled()
            if (!result.isCancelled()) {
                if (result.isSuc()) {
                    mProgressDialog.setMessage("Esptouch success, bssid = "
                            + result.getBssid() + ",InetAddress = "
                            + result.getInetAddress().getHostAddress());
                } else {
                    mProgressDialog.setMessage("Esptouch fail");
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_connect_wifi, menu);
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

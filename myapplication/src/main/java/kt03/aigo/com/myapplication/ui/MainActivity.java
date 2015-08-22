package kt03.aigo.com.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.aigo.usermodule.ui.util.ToastUtil;
import com.etek.ircore.RemoteCore;

import java.util.ArrayList;
import java.util.List;

import kt03.aigo.com.myapplication.R;
import kt03.aigo.com.myapplication.business.Module;
import kt03.aigo.com.myapplication.business.bean.Brand;
import kt03.aigo.com.myapplication.business.util.Globals;
import kt03.aigo.com.myapplication.business.util.Tools;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText input = (EditText) findViewById(R.id.tv_input);

        int[] array = {
                137, 63, 20, 48, 20, 12, 19, 12, 19, 12, 19, 48, 20,
                12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 12, 19, 12, 19, 49, 19,
                12, 19, 12, 19, 12, 19, 49, 19, 12, 19, 12, 19, 12, 19, 48, 20,
                12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 12, 19, 12, 19, 48, 20,
                12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 12, 19, 12, 19, 48, 20,
                12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 12, 19,
                48, 20, 12, 19, 12, 19, 49, 19, 49, 19, 12, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 49, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 12, 19, 48, 20, 12, 19, 48, 20, 12, 19,
                12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19, 12, 19,
                12, 19, 12, 19, 12, 19, 48, 20, 48, 20, 12, 19, 48, 20, 12, 19,
                12, 19, 12, 19, 48, 20, 3843};


        byte[] codes = RemoteCore.prontoToETcode(38000, array);
        input.setText(Tools.bytesToHexString(codes));

        Module.getInstance().getBrandList(new Module.OnPostListener<List<Brand>>() {
            @Override
            public void onSuccess(List<Brand> result) {

                ToastUtil.showToast(getApplicationContext(), result.get(0).toString());
                Globals.MBrands = (ArrayList)result;
                Intent i = new Intent(MainActivity.this,
                        BrandListActivity.class);
                startActivity(i);

            }

            @Override
            public void onFailure(String err) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

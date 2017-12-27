package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.fpt.frt_minventory.Adapter.AdapterSelectWarehouseCode;
import vn.com.fpt.frt_minventory.Model.PropertiesWarehouseCode;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

/**
 * Created by ADMIN on 11/28/2017.
 */

    public class SelectWarehouseCode extends Activity {
    ListView lv;
    ImageView img_back;
    Context context = this;
    AdapterSelectWarehouseCode adapterSelectWarehouseCode;
    List<PropertiesWarehouseCode> arr = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_warehouse_code);

        img_back = (ImageView)findViewById(R.id.btn_back_selec_warehouse);
        lv = (ListView)findViewById(R.id.lv_warehouse_code);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra("WarehouseCode", arr.get(i).getWhsCode());
                setResult(InventoryDetail.WAREHOUSE_CODE, intent);
                finish();
            }
        });


        adapterSelectWarehouseCode = new AdapterSelectWarehouseCode(context, arr);
        lv.setAdapter(adapterSelectWarehouseCode);
    }
    protected void addlist(ArrayList<PropertiesWarehouseCode> _list) {
        arr = _list;
    }
    @Override
    protected void onResume() {
        super.onResume();
        selectwarehousecode();

    }
    protected void selectwarehousecode(){
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang tải thông tin...", true, false);
        new AsyncTask<Void, Void, List<PropertiesWarehouseCode>>() {
            @Override
            protected List<PropertiesWarehouseCode> doInBackground(Void... params) {
                try {

                    List<PropertiesWarehouseCode> arr = APIService.getSelectWarehouseCodes();
                    return arr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<PropertiesWarehouseCode> propertiesWarehouseCodes) {
                arr.clear();
                progressDialog.dismiss();
                if (propertiesWarehouseCodes != null && propertiesWarehouseCodes.size() > 0) {
                    arr.addAll(propertiesWarehouseCodes);
                    adapterSelectWarehouseCode.notifyDataSetChanged();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SelectWarehouseCode.this).create();
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("Kết nối internet bị gián đoạn");
                    alertDialog.setIcon(R.drawable.ic_dialog_close_light);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đóng",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}

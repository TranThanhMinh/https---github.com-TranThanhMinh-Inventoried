package vn.com.fpt.frt_minventory.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Adapter.AdapterInventoryOnDay;
import vn.com.fpt.frt_minventory.Model.PropertiesInventoryOnDay;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;

public class InventoryOnDay extends Activity {
    Context context = this;
    AdapterInventoryOnDay adapter;
    public static int RC_IVNENTORY_FINISH = 11;
    EditText search;
    ListView lvItem;
    ImageView btn_reload_inventory_onday, btn_back;
    List<PropertiesInventoryOnDay> arrItemInventoryOnday = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iventory_on_day);
        loadinventoryonday();
        lvItem = (ListView) findViewById(R.id.lv_On_Day);
        search = (EditText) findViewById(R.id.search_inventory_on_day);

        btn_reload_inventory_onday = (ImageView) findViewById(R.id.btn_reload_inventory_onday);
        btn_back = (ImageView)findViewById(R.id.btn_back_inventory_onday);

        btn_reload_inventory_onday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String find = search.getText().toString();
                adapter.filter(find);
            }
        });
//        an ban phim tren man hinh
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(InventoryOnDay.this, InventoryDetail.class);
                bundle.putString("sophieu", Integer.toString(arrItemInventoryOnday.get(i).getDocEntry()));
                bundle.putString("CreateBy", arrItemInventoryOnday.get(i).getCreateBy());
                Log.e("getCreateBy",arrItemInventoryOnday.get(i).getCreateBy());
                bundle.putInt("LoaiSPCode", arrItemInventoryOnday.get(i).getLoaiSPCode());
                bundle.putString("trangthai", arrItemInventoryOnday.get(i).getLineStatusCode());
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, RC_IVNENTORY_FINISH);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadinventoryonday();
    }

    protected void loadinventoryonday() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang tải thông tin...", true, false);
        new AsyncTask<Void, Void, List<PropertiesInventoryOnDay>>() {
            @Override
            protected List<PropertiesInventoryOnDay> doInBackground(Void... params) {
                try {

                    List<PropertiesInventoryOnDay> arrItem = APIService.getInventoryOnDay();
                    return arrItem;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<PropertiesInventoryOnDay> inventoryonday) {
                arrItemInventoryOnday.clear();
                progressDialog.dismiss();
                if (inventoryonday != null) {
                    if (inventoryonday.size() == 0) {
                        Toasty.warning(InventoryOnDay.this, "Danh sách trống!", 1).show();
                    } else {
                        arrItemInventoryOnday.addAll(inventoryonday);

                    }
                    adapter = new AdapterInventoryOnDay(context, arrItemInventoryOnday);
                    lvItem.setAdapter(adapter);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoryOnDay.this).create();
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_IVNENTORY_FINISH) {
            onResume();
        }
    }

}

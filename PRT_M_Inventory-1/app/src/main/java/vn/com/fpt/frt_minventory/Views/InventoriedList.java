package vn.com.fpt.frt_minventory.Views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Adapter.AdapterInventoryList;
import vn.com.fpt.frt_minventory.Model.InventoriedListResult;
import vn.com.fpt.frt_minventory.Model.PropertiesInventoryList;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.APIService;


public class InventoriedList extends AppCompatActivity {
    AdapterInventoryList adapter;
    Context context = this;
    TextView txt_Time_in, txt_Time_out;
    EditText edt_DocEntry_search;
    ListView listviewdetail;
    ImageView btn_back;
    Button btn_search;
    int check;
    String Time_in = "", Time_out = "";
    String Docentry_Search;
    List<InventoriedListResult> inventoried = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventoried_list);
        txt_Time_in = (TextView) findViewById(R.id.txt_Time_in_Search);
        txt_Time_out = (TextView) findViewById(R.id.txt_Time_out_Search);
        btn_search = (Button)findViewById(R.id.btn_search);
        edt_DocEntry_search = (EditText)findViewById(R.id.txt_DocEntry_search);

        txt_Time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fncTimein();
            }
        });
        txt_Time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fncTimeout();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fncSearch();
            }
        });


        listviewdetail = (ListView) findViewById(R.id.lv_detail);
        btn_back = (ImageView) findViewById(R.id.btn_back_inventoried_list);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listviewdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Docentry_Search =inventoried.get(position).getDocEntry()+"";
                Intent intent = new Intent(InventoriedList.this, ListInventoried.class);
                intent.putExtra("docentry", Docentry_Search);
                intent.putExtra("docstatus", inventoried.get(position).getLineStatusCode());
                intent.putExtra("check", "0");
                startActivity(intent);
            }
        });


        final ArrayList<PropertiesInventoryList> arrItem = new ArrayList<>();

        //        an ban phim tren man hinh
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // co dinh cac thuoc tinh khi ban phim mo len
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    // Show dialog Date picker
    private void fncTimein() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");

                txt_Time_in.setText(simpleDateFormat.format(calendar.getTime()));
                Time_in = (simpleDate.format(calendar.getTime()));
            }
        }, date, month, year);
        datePickerDialog.updateDate(year, month, date);
        datePickerDialog.show();
    }

    private void fncTimeout() {
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");

                txt_Time_out.setText(simpleDateFormat.format(calendar.getTime()));
                Time_out = (simpleDate.format(calendar.getTime()));
            }
        }, date, month, year);
        datePickerDialog.updateDate(year, month, date);
        datePickerDialog.show();
    }

    private void fncSearch(){


        final ProgressDialog progressDialog = ProgressDialog.show(context, "Xin chờ", "Đang tìm phiếu....", true, false);
        new AsyncTask<Void, Void, List<InventoriedListResult>>() {
            @Override
            protected List<InventoriedListResult> doInBackground(Void... params) {
                try {
                    List<InventoriedListResult> inventoriedListResult = APIService.getInventoriedListResult(Time_in, Time_out, edt_DocEntry_search.getText().toString());
                    return inventoriedListResult;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(List<InventoriedListResult> inventoriedListResult) {

                progressDialog.dismiss();
                if (inventoriedListResult != null) {
                    if (inventoriedListResult.size() == 0) {
                        Toasty.warning(InventoriedList.this, "Danh sách trống!").show();
                    } else {
                        inventoried=inventoriedListResult;
                        adapter = new AdapterInventoryList(context, inventoriedListResult);
                        listviewdetail.setAdapter(adapter);
                    }

                    /*for(InventoriedListResult result :listResults ){
                        Log.e("DocEntry", result.getDocEntry()+"");
                    }*/
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(InventoriedList.this).create();
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

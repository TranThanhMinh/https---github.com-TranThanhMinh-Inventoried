package vn.com.fpt.frt_minventory.Views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.fpt.frt_minventory.Adapter.AdapterInventoryJob;
import vn.com.fpt.frt_minventory.Model.Job;
import vn.com.fpt.frt_minventory.Model.ResultJob;
import vn.com.fpt.frt_minventory.Model.upload;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Services.KeyApi;
import vn.com.fpt.frt_minventory.Services.Url;

/**
 * Created by minhtran on 12/26/17.
 */

public class SearchJob extends AppCompatActivity implements View.OnClickListener {
    private Button btn_search;
    private TextView txt_Time_in, txt_Time_out;
    String Time_in = "", Time_out = "";
    Retrofit retrofit;
    private EditText txt_DocEntry_search;
    private List<Job> searchJob = new ArrayList<>();
    private AdapterInventoryJob adapter;
    private ListView lv_detail;
    private ImageView btn_back_inventoried_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventoried_job);
        btn_back_inventoried_list = (ImageView) findViewById(R.id.btn_back_inventoried_list);
        txt_Time_in = (TextView) findViewById(R.id.txt_Time_in_Search);
        txt_Time_out = (TextView) findViewById(R.id.txt_Time_out_Search);
        btn_search = (Button) findViewById(R.id.btn_search);
        txt_DocEntry_search = (EditText) findViewById(R.id.txt_DocEntry_search);
        lv_detail = (ListView) findViewById(R.id.lv_detail);
        txt_Time_in.setOnClickListener(this);
        txt_Time_out.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_back_inventoried_list.setOnClickListener(this);
        retrofit = getConnect();

    }

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

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;

    }
    public void funcSearchJob() {
        KeyApi api = retrofit.create(KeyApi.class);
        upload up = new upload();
        up.setFromDate(Time_in);
        up.setToDate(Time_out);
        up.setSoPhieu(txt_DocEntry_search.getText().toString());
        up.setShopCode("");
        Call<ResultJob> call = api.InvetorySearchJob(up);
        call.enqueue(new Callback<ResultJob>() {
            @Override
            public void onResponse(Call<ResultJob> call, Response<ResultJob> response) {
                searchJob = response.body().getSearchJob();
                if (searchJob != null && searchJob.size() > 0) {
                    adapter = new AdapterInventoryJob(SearchJob.this, searchJob);
                    lv_detail.setAdapter(adapter);
                } else lv_detail.setAdapter(null);
            }
            @Override
            public void onFailure(Call<ResultJob> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == txt_Time_in) {
            fncTimein();
        } else if (view == txt_Time_out) {
            fncTimeout();
        } else if (view == btn_search) {
            if (Time_in.equals("")) {
                Toast.makeText(this, "Vui lòng nhập từ ngày", Toast.LENGTH_SHORT).show();
            } else if (Time_out.equals("")) {
                Toast.makeText(this, "Vui lòng nhập đến ngày", Toast.LENGTH_SHORT).show();
            } else
                funcSearchJob();
        } else if (view == btn_back_inventoried_list) finish();

    }
}

package vn.com.fpt.frt_minventory.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import vn.com.fpt.frt_minventory.Adapter.Adapter_SL_Phieu_KK;
import vn.com.fpt.frt_minventory.Model.Properties_SL_phieu_KK;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/15/2017.
 */

public class SL_Phieu_KK extends AppCompatActivity {
    ListView lv;
    ImageView img_back;
    List<Properties_SL_phieu_KK> arrItem = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sl_phieu_kk);
        lv = (ListView)findViewById(R.id.lv_sl_phieu_kk);
        img_back = (ImageView)findViewById(R.id.img_back_sl_phieu_kk);


        arrItem.add(new Properties_SL_phieu_KK("1"));
        arrItem.add(new Properties_SL_phieu_KK("2"));
        arrItem.add(new Properties_SL_phieu_KK("3"));
        arrItem.add(new Properties_SL_phieu_KK("4"));
        arrItem.add(new Properties_SL_phieu_KK("5"));
        arrItem.add(new Properties_SL_phieu_KK("6"));
        arrItem.add(new Properties_SL_phieu_KK("7"));
        arrItem.add(new Properties_SL_phieu_KK("8"));
        arrItem.add(new Properties_SL_phieu_KK("9"));
        arrItem.add(new Properties_SL_phieu_KK("10"));

        Adapter_SL_Phieu_KK adapter = new Adapter_SL_Phieu_KK(this, R.layout.item_sl_phieu_kk, (ArrayList<Properties_SL_phieu_KK>) arrItem);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = getIntent();
                i.putExtra("SoPhieu", arrItem.get(position).getSoluong());
                setResult(Calendar_Inventory.SL_PHIEU_KK, i);
                finish();
            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

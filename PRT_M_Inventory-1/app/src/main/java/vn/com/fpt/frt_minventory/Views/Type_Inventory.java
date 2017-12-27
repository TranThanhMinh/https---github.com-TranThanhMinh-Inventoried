package vn.com.fpt.frt_minventory.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.com.fpt.frt_minventory.Adapter.AdapterTypeInventory;
import vn.com.fpt.frt_minventory.Model.PropertiesTypeInventory;
import vn.com.fpt.frt_minventory.Model.Properties_SL_phieu_KK;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/15/2017.
 */

public class   Type_Inventory extends AppCompatActivity {

    ImageView img_back;
    ListView lv;
    List<PropertiesTypeInventory> arrItem = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_inventory);
        img_back = (ImageView) findViewById(R.id.img_back_loai_phieu);
        lv = (ListView)findViewById(R.id.lv_loai_phieu);

        arrItem.add(new PropertiesTypeInventory("1 - Sản phẩm thường"));
        arrItem.add(new PropertiesTypeInventory("2 - Sản phẩm demo"));
        arrItem.add(new PropertiesTypeInventory("3 - Vật phẩm khuyến mãi"));

        AdapterTypeInventory adapterTypeInventory = new AdapterTypeInventory(Type_Inventory.this, R.layout.item_type_inventory, arrItem);
        lv.setAdapter(adapterTypeInventory);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("Loaiphieu", arrItem.get(position).getLoaiphieu());
                setResult(Calendar_Inventory.TYPE_INVENTORY, intent);
                finish();
            }
        });

    }
}

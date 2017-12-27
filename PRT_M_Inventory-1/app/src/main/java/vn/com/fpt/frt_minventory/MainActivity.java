package vn.com.fpt.frt_minventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.fpt.frt_minventory.Views.Calendar_Inventory;
import vn.com.fpt.frt_minventory.Views.CameraActivity;
import vn.com.fpt.frt_minventory.Views.InventoriedList;
import vn.com.fpt.frt_minventory.Views.InventoryOnDay;
import vn.com.fpt.frt_minventory.Views.LoginActivity;
import vn.com.fpt.frt_minventory.Views.SearchJob;
import vn.com.fpt.frt_minventory.Views.UnlockInventory;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2, logout, img3, img4,img5;
    TextView txt1, txt2, txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1 = (ImageView) findViewById(R.id.img_Inventory_OnDay);
        img2 = (ImageView) findViewById(R.id.img_Invetoried_List);
        img3 = (ImageView) findViewById(R.id.img_unlock);
        img4 = (ImageView) findViewById(R.id.img_calendar);
        img5 = (ImageView) findViewById(R.id.img_search_job );
        logout = (ImageView) findViewById(R.id.img_logout);
        txt1 = (TextView) findViewById(R.id.txt_User_Login);
        txt2 = (TextView) findViewById(R.id.txt_Code_Main);
        txt3 = (TextView) findViewById(R.id.txt_JobTitle);
        final Intent intent = getIntent();
        String EmployeeName = intent.getStringExtra("EmployeeName");
        String ShopName = intent.getStringExtra("ShopName");
        String JobTitleb = intent.getStringExtra("JobTitle_name");
        txt1.setText(EmployeeName);
        txt2.setText(ShopName);
        txt3.setText(JobTitleb);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InventoryOnDay.class);
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InventoriedList.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnlockInventory.class);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calendar_Inventory.class);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchJob.class);
                startActivity(intent);
            }
        });
    }

}

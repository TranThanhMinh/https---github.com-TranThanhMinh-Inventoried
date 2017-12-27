package vn.com.fpt.frt_minventory.Views;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import vn.com.fpt.frt_minventory.R;

/**
 * Created by minhtran on 12/21/17.
 */

public class ActivityShowImage extends AppCompatActivity {
    ImageView imView, inBack;
    String uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image);
        imView = (ImageView) findViewById(R.id.imView);
        inBack = (ImageView) findViewById(R.id.inBack);
        inBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
        uri = (String) getIntent().getSerializableExtra("uri");
        if (uri.contains("http")) {
            Picasso.with(this).load(uri).resize(500, 500).into(imView);
            inBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            Picasso.with(this).load(Uri.parse(uri)).resize(500, 500).into(imView);
            inBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

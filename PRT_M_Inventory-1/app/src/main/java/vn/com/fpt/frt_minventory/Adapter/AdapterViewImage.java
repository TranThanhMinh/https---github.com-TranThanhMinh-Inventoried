package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.Model.SearchImage;
import vn.com.fpt.frt_minventory.Model.ViewImage;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by minhtran on 12/20/17.
 */

public class AdapterViewImage extends BaseAdapter {
    private Context context;
    private List<ViewImage> list;


    public funcDelete getDelete() {
        return Delete;
    }

    public void setDelete(funcDelete delete) {
        Delete = delete;
    }

    private funcDelete Delete;

    public interface funcDelete {
        void click(int i);
    }

    public AdapterViewImage(Context context, List<ViewImage> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_camera, null);
        ImageView imView = (ImageView) v.findViewById(R.id.imView);
        ImageView imDelete = (ImageView) v.findViewById(R.id.imView1);
        imDelete.setVisibility(View.GONE);
        final ViewImage photo = list.get(i);
             Picasso.with(context).load(photo.getDomain()).resize(300, 300).into(imView);
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete.click(i);
                }
            });
        return v;
    }
}
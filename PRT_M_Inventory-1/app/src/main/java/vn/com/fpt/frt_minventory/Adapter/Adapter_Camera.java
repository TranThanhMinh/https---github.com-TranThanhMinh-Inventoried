package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by minhtran on 12/20/17.
 */

public class Adapter_Camera extends BaseAdapter {
    private Context context;
    private List<Photo> list;


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

    public Adapter_Camera(Context context, List<Photo> list, funcDelete Delete) {
        this.context = context;
        this.list = list;
        this.Delete = Delete;
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
        final Photo photo = list.get(i);
        if (photo.getUri().contains("http")) {
            Picasso.with(context).load(photo.getUri()).resize(300, 300).into(imView);
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete.click(i);
                }
            });
        } else {
            Picasso.with(context).load(Uri.parse(photo.getBitmap())).resize(300, 300).into(imView);
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete.click(i);
                }
            });
        }
        return v;
    }
}
package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Model.CameraResult;
import vn.com.fpt.frt_minventory.Model.PropertiesInventoryOnDay;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Views.CameraActivity;

/**
 * Created by ADMIN on 12/7/2017.
 */

public class AdapterCamera extends ArrayAdapter<CameraResult> {

    ImageView photo, delete;
    Context context;
    int LayoutId;
    List<CameraResult> myArr;

    public AdapterCamera(@NonNull Context context, List<CameraResult> _myArr) {
        super(context, -1, _myArr);
        this.context = context;
        this.myArr = _myArr;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        final CameraResult chill= (CameraResult)getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_gallery, null);
        }

        if (myArr.size() > 0 && position >= 0) {
            final ImageView photo = (ImageView)convertView.findViewById(R.id.img_gallery);
            photo.setImageBitmap(myArr.get(position).getGallery());
            final ImageView delete = (ImageView)convertView.findViewById(R.id.img_delete_photo);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Xóa ảnh này!");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Có",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    myArr.remove(chill);
                                    notifyDataSetChanged();
                                    Toasty.success(context, "Đã xóa").show();
                                }
                            });

                    builder1.setNegativeButton(
                            "Không",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });
                }

        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }
}

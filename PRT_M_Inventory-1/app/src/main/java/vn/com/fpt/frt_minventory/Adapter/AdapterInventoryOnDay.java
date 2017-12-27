package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.com.fpt.frt_minventory.Model.PropertiesInventoryOnDay;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 11/14/2017.
 */

public class AdapterInventoryOnDay extends ArrayAdapter<PropertiesInventoryOnDay> {
 Context context ;
  List<PropertiesInventoryOnDay> myArray ;
 List<PropertiesInventoryOnDay> AddmyArray ;
    int layoutId;
 ImageView imgv;


    public AdapterInventoryOnDay(@NonNull Context context, List<PropertiesInventoryOnDay> _myArray) {
       super(context, -1,_myArray);
        this.context = context;
        this.layoutId = layoutId;
        this.AddmyArray = _myArray;
        preparelist();
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
     final PropertiesInventoryOnDay chill= (PropertiesInventoryOnDay)getItem(position);
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.item_inventory_on_day,null);
        }

        if(AddmyArray.size()>0 && position>=0)
        {
            if(chill.getLineStatusCode().equals("O")){
                imgv = (ImageView)convertView.findViewById(R.id.img_stt);
                imgv.setImageResource(R.drawable.o);
            }else if(chill.getLineStatusCode().equals("C")){
                imgv = (ImageView)convertView.findViewById(R.id.img_stt);
                imgv.setImageResource(R.drawable.c);
            }else{
                imgv = (ImageView)convertView.findViewById(R.id.img_stt);
                imgv.setImageResource(R.drawable.f);
            }

             TextView txtThoiGianKiemKe=(TextView)
                    convertView.findViewById(R.id.txt_Thoi_Gian_Kiem_Ke);
            txtThoiGianKiemKe.setText(chill.getFromTime());
            TextView txtThoiGianKetThucKiemKe = (TextView) convertView.findViewById(R.id.txt_thoi_gian_ket_thuc_kk);
            txtThoiGianKetThucKiemKe.setText(chill.getToTime());
             TextView txtThuTuPhieu=(TextView)
                    convertView.findViewById(R.id.txt_Thu_Tu_Phieu);
            txtThuTuPhieu.setText(Integer.toString(chill.getDocEntry()));
             TextView txtLoaiPhieu=(TextView)
                    convertView.findViewById(R.id.txt_Loai_Phieu);
           txtLoaiPhieu.setText(chill.getLoaiSP());
              TextView txtNgayKiemKe=(TextView)
                    convertView.findViewById(R.id.txt_ngay_kiem_ke);
            txtNgayKiemKe.setText(chill.getFromDate());
        }
        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }

// loc danh sach tim kiem
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()); // chuyển chữ IN thành chữ thường
        AddmyArray.clear();
        if (charText.length() == 0) {
            AddmyArray.addAll(myArray);
        }
        else
        {
            for (PropertiesInventoryOnDay sp : myArray)
            {
                if (Integer.toString(sp.getDocEntry()).toLowerCase(Locale.getDefault()).contains(charText))
                {
                    AddmyArray.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Dung ham nay de day data tu list AddmyArr sang myArr de khong bi clear
    public void preparelist(){
        myArray =new ArrayList<>();
        for(PropertiesInventoryOnDay propertiesInventoryOnDay:this.AddmyArray){
            PropertiesInventoryOnDay itemnew =new PropertiesInventoryOnDay();
            itemnew=propertiesInventoryOnDay;
            myArray.add(itemnew);
        }
    }
}


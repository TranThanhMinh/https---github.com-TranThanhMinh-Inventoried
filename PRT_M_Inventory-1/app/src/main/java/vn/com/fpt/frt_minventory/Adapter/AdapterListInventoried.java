package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import vn.com.fpt.frt_minventory.Model.PropertiesInventoryOnDay;
import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Model.ListInventoriedResult;
import vn.com.fpt.frt_minventory.Views.ListInventoried;

/**
 * Created by ADMIN on 11/28/2017.
 */

public class AdapterListInventoried extends ArrayAdapter<ListInventoriedResult> {

    Context context;
    List<ListInventoriedResult> myarray;
    List<ListInventoriedResult> Addmyarray;
    String Status;

//    public clickDelete getClickdelete() {
//        return clickdelete;
//    }
//
//    public void setClickdelete(clickDelete clickdelete) {
//        this.clickdelete = clickdelete;
//    }
//
//    private clickDelete clickdelete;
//    public interface clickDelete{
//        void delete();
//    }


    public AdapterListInventoried(@NonNull Context context, int resource, List<ListInventoriedResult> _myarray, String status) {
        super(context, 0, _myarray);
        this.context = context;
        this.Addmyarray = _myarray;
        this.Status = status;
      //  this.clickdelete = clickdelete;
        preparelist();
    }


    public View getView(int position, View convertView,
                        ViewGroup parent) {
        final ListInventoriedResult chill= (ListInventoriedResult)getItem(position);
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.item_list_inventoried,null);
        }

        if(myarray.size()>0 && position>=0)
        {
            TextView txt_ten_SP=(TextView)
                    convertView.findViewById(R.id.txt_List_Loai_SP);

            txt_ten_SP.setText(chill.getItemName());
            TextView tvNumber=(TextView) convertView.findViewById(R.id.tvNumber);
            tvNumber.setText(position+1+"");
            TextView txt_Ma_SP=(TextView)
                    convertView.findViewById(R.id.txt_list_MaSanPham);
            txt_Ma_SP.setText(chill.getItemCode());
            TextView txt_Serial=(TextView)
                    convertView.findViewById(R.id.txt_list_Serial);
            txt_Serial.setText(chill.getSerial());
            TextView txt_Ma_Kho=(TextView)
                    convertView.findViewById(R.id.txt_list_MaKho);
            txt_Ma_Kho.setText(chill.getWhs_Code());
            TextView txt_SL_Thuc_Te = (TextView)
                    convertView.findViewById(R.id.txt_list_SL_ThucTe);
            txt_SL_Thuc_Te.setText(Integer.toString(chill.getR_Quantity()));
            TextView txt_SL_He_Thong = (TextView)
                    convertView.findViewById(R.id.txt_list_SL_HeThong);
            txt_SL_He_Thong.setText(Integer.toString(chill.getSys_Quantiy()));

            final ImageView imgv_delete_list = (ImageView)convertView.findViewById(R.id.img_delete_item_list_inventoried);
             ImageView imView = (ImageView)convertView.findViewById(R.id.imView);
            if(chill.isCheck()){
                imView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_picture));
            }
            else {
                imView.setImageDrawable(context.getResources().getDrawable(R.drawable.image));
            }
            if (Status.equals("F") || Status.equals("C"))
            {
                imgv_delete_list.setVisibility(View.INVISIBLE);
            }
            else
            {
                imgv_delete_list.setVisibility(View.VISIBLE);
            }
            imgv_delete_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage("Bạn có muốn xóa phiếu này?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Có",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Addmyarray.remove(chill);
                                    notifyDataSetChanged();
                                    Toasty.success(context, "Xóa thành công!").show();
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


    // loc danh sach tim kiem
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Addmyarray.clear();
        if (charText.length() == 0) {
            Addmyarray.addAll(myarray);
        }
        else
        {
            for (ListInventoriedResult sp : myarray)
            {
                if (sp.getItemCode().toLowerCase(Locale.getDefault()).contains(charText)|| sp.getItemName().toLowerCase(Locale.getDefault()).contains(charText)||sp.getSerial().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Addmyarray.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void preparelist(){
        myarray =new ArrayList<>();
        for(ListInventoriedResult listInventoriedResult:this.Addmyarray){
            ListInventoriedResult itemnew =new ListInventoriedResult();
            itemnew = listInventoriedResult;
            myarray.add(itemnew);
        }
    }

}


package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.fpt.frt_minventory.Model.InventoriedListResult;
import vn.com.fpt.frt_minventory.Model.Job;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 11/17/2017.
 */

public class AdapterInventoryJob extends ArrayAdapter<Job> {

    Context context;

    List<Job> myArray ;
    int layoutId;
    public AdapterInventoryJob(Context context, List<Job> arr) {
        super(context, -1, arr);
        this.context= context;
        this.layoutId = layoutId;
        this.myArray = arr;

    }



    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_search_job,null);

        if(myArray.size()>0 && position>=0)
        {
            final TextView txtDocEntry=(TextView)
                    convertView.findViewById(R.id.txt_DocEntry_);
            txtDocEntry.setText(Integer.toString(myArray.get(position).getDocEntry()));

            final TextView txtLoai_SP=(TextView)
                    convertView.findViewById(R.id.txt_Loai_SP_Search);
            txtLoai_SP.setText(myArray.get(position).getLoaiSP());

            final TextView txtFromTime=(TextView)
                    convertView.findViewById(R.id.txt_Time_in);
            txtFromTime.setText(myArray.get(position).getFromTime());

            final TextView txtToTime=(TextView)
                    convertView.findViewById(R.id.txt_Time_out);
            txtToTime.setText(myArray.get(position).getToTime());

            final TextView txtLineStatus=(TextView)
                    convertView.findViewById(R.id.txt_Stt_Search);
            txtLineStatus.setText(myArray.get(position).getDocStatus());

            final TextView txtFromDate=(TextView)
                    convertView.findViewById(R.id.txt_Date_Search);
            txtFromDate.setText(myArray.get(position).getFromDate());

            final TextView txtNguoiKK=(TextView)
                    convertView.findViewById(R.id.txt_user_Search);
            txtNguoiKK.setText(myArray.get(position).getCreateBy());


        }
        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }


}

package vn.com.fpt.frt_minventory.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import vn.com.fpt.frt_minventory.Model.Properties_SL_phieu_KK;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/15/2017.
 */

public class Adapter_SL_Phieu_KK extends ArrayAdapter<Properties_SL_phieu_KK> {

    Activity context;
    ArrayList<Properties_SL_phieu_KK> myArray = null;
    int layoutId;

    public Adapter_SL_Phieu_KK(Activity context, int layoutId, ArrayList<Properties_SL_phieu_KK> _myArr) {
        super(context, layoutId, _myArr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = _myArr;
    }
        public View getView(int postion, View convertView, ViewGroup parent){
        final  Properties_SL_phieu_KK chill = (Properties_SL_phieu_KK)getItem(postion);
        if(convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }   if(myArray.size()>0 && postion>=0){
           TextView txt_SL = (TextView)convertView.findViewById(R.id.txt_Sl_phieu_DatLich);
           txt_SL.setText(chill.getSoluong());
        }
        return convertView;
    }

}
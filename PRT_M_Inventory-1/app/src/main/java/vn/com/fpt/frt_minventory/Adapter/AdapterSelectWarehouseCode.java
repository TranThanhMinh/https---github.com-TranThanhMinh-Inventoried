package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.fpt.frt_minventory.R;
import vn.com.fpt.frt_minventory.Model.PropertiesWarehouseCode;

/**
 * Created by ADMIN on 11/28/2017.
 */

public class AdapterSelectWarehouseCode extends ArrayAdapter<PropertiesWarehouseCode> {
    Context context;
    List<PropertiesWarehouseCode> myArr;

    public AdapterSelectWarehouseCode(@NonNull Context context, List<PropertiesWarehouseCode> myArr) {
        super(context,-1, myArr);
        this.context = context;
        this.myArr = myArr;
    }

    public View getView(int position, View convertView,ViewGroup parent) {
        final PropertiesWarehouseCode chill= (PropertiesWarehouseCode) getItem(position);
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.item_warehouse_code,null);
        }

        if(myArr.size()>0 && position>=0)
        {
            TextView txtWhsCode = (TextView)convertView.findViewById(R.id.txtWhsCode);
            txtWhsCode.setText(chill.getWhsCode());

            TextView txtWhsName = (TextView)convertView.findViewById(R.id.txtWhsName);
            txtWhsName.setText(chill.getWhsName());
            }
        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }
}


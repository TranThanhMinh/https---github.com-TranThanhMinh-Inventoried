package vn.com.fpt.frt_minventory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.fpt.frt_minventory.Model.PropertiesTypeInventory;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/15/2017.
 */

public class AdapterTypeInventory extends ArrayAdapter<PropertiesTypeInventory> {

    Activity context;
    List<PropertiesTypeInventory> myArray = null;
    int layoutId;

    public AdapterTypeInventory( Activity context, int layoutId, List<PropertiesTypeInventory> _myArr) {
        super(context, layoutId, _myArr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = _myArr;
    }
    public View getView (int position, View convertView, ViewGroup viewGroup){

        final  PropertiesTypeInventory chill = (PropertiesTypeInventory)getItem(position);
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, null);
        }
        if(myArray.size()>0 && position >=0){
            TextView txtLoaiphieu = (TextView)convertView.findViewById(R.id.txt_loai_phieu_Datlich);
            txtLoaiphieu.setText(chill.getLoaiphieu());
        }
        return convertView;
    }
}

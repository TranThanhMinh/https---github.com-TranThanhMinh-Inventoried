package vn.com.fpt.frt_minventory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.com.fpt.frt_minventory.Model.PropertiesArea_Calendar;
import vn.com.fpt.frt_minventory.R;

/**
 * Created by ADMIN on 12/12/2017.
 */

public class   AdapterArea_Calendar extends ArrayAdapter<PropertiesArea_Calendar> {
    List<PropertiesArea_Calendar> myArray;
    List<PropertiesArea_Calendar> Filter_Vung_myArray;
    Context context;
    CheckBox check;
    public String _id ="";
    public String _name="";

    public _Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(_Onclick onclick) {
        this.onclick = onclick;
    }

    private _Onclick onclick;
    public interface _Onclick{
        void click(String _id,String _name);
    }

    public AdapterArea_Calendar(@NonNull Context context, List<PropertiesArea_Calendar> myArrArea,_Onclick onclick) {
        super(context, -1, myArrArea);
        this.context = context;
        this.Filter_Vung_myArray = myArrArea;
        this.onclick = onclick;
        preparelist();
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        final PropertiesArea_Calendar chill = (PropertiesArea_Calendar) getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_area_activity, null);
        }

        if (Filter_Vung_myArray.size() > 0 && position >= 0 ) {
            check = (CheckBox) convertView.findViewById(R.id.checkbox_Area);
            if(chill.isCheck()){
                check.setChecked(true);
                Filter_Vung_myArray.get(position).setCheck(true);
                _name += chill.getNAME() + ", ";
                _id +=chill.getID()+",";
            }
            else {
                check.setChecked(false);
                Filter_Vung_myArray.get(position).setCheck(false);
                _name = _name.replace(chill.getNAME() + ", ","");
                _id =_id.replace(chill.getID()+",","");

            }
            check.setText(chill.getNAME().toString());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(chill.isCheck()){
                        Filter_Vung_myArray.get(position).setCheck(false);
                        _name = _name.replace(chill.getNAME() + ", ","");
                        _id =_id.replace(chill.getID()+",","");
                    }else {
                        Filter_Vung_myArray.get(position).setCheck(true);
                        _name += chill.getNAME() + ", ";
                       _id +=chill.getID()+",";

                    }

                    Log.d("check", _id );
                    onclick.click(_id,_name);

                }
            });
        }
        return convertView;//trả về View này, tức là trả luôn
        //về các thông số mới mà ta vừa thay đổi
    }


    // loc danh sach Vung
    public void filter(String ID) {
        List<String>l_rootID =Arrays.asList(ID.split(","));
        Filter_Vung_myArray.clear();
        for (PropertiesArea_Calendar sp : myArray)
        {
            if (sp.getRootID()!=null && l_rootID.indexOf(sp.getRootID()) >=0)
            {
                Filter_Vung_myArray.add(sp);
            }

        }
    }


    // Dung ham nay de day data tu list AddmyArr sang Filter_myArray de khong bi clear
    public void preparelist(){
        myArray =new ArrayList<>();
        for(PropertiesArea_Calendar propertiesArea_calendar:this.Filter_Vung_myArray){
            PropertiesArea_Calendar itemnew = new PropertiesArea_Calendar();
            itemnew=propertiesArea_calendar;
            myArray.add(itemnew);
        }
    }

}

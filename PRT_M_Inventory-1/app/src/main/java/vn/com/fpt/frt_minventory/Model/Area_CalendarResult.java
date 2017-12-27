package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/18/2017.
 */

public class Area_CalendarResult implements Serializable {
    int Result;
    String U_Desc;

    public  Area_CalendarResult(){

    }
    public Area_CalendarResult(int result, String u_Desc){
        this.Result = result;
        this.U_Desc = u_Desc;
    }
    public static Area_CalendarResult fromJson(String json){
        try{
            Gson gson = new Gson();
            List<Area_CalendarResult> list = gson.fromJson(json, new TypeToken<List<Area_CalendarResult>>(){
            }.getType());
            return list.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getU_Desc() {
        return U_Desc;
    }

    public void setU_Desc(String u_Desc) {
        U_Desc = u_Desc;
    }
}

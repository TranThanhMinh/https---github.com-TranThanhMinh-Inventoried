package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by ADMIN on 12/15/2017.
 */

public class Properties_SL_phieu_KK {
    String soluong;

    public Properties_SL_phieu_KK(String soluong){
        this.soluong = soluong;
    }
    public static List<Properties_SL_phieu_KK> fromJsonArray(String jsonArray) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<Properties_SL_phieu_KK>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }
}
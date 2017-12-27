package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by ADMIN on 11/27/2017.
 */

public class PropertiesWarehouseCode {
    String U_Code_SH;
    String U_WHS_TYPE;
    String WhsCode;
    String WhsName;

    public PropertiesWarehouseCode(String u_Code_SH, String u_WHS_TYPE, String whsCode, String whsName){
        this.U_Code_SH = u_Code_SH;
        this.U_WHS_TYPE = u_WHS_TYPE;
        this.WhsCode = whsCode;
        this.WhsName = whsName;
    }
    public static List<PropertiesWarehouseCode> fromJsonArray(String jsonArray) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<PropertiesWarehouseCode>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getU_Code_SH() {
        return U_Code_SH;
    }

    public void setU_Code_SH(String u_Code_SH) {
        U_Code_SH = u_Code_SH;
    }

    public String getU_WHS_TYPE() {
        return U_WHS_TYPE;
    }

    public void setU_WHS_TYPE(String u_WHS_TYPE) {
        U_WHS_TYPE = u_WHS_TYPE;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getWhsName() {
        return WhsName;
    }

    public void setWhsName(String whsName) {
        WhsName = whsName;
    }
}

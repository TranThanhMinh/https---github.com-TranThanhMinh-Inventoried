package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/29/2017.
 */

public class InventoryResult implements Serializable {
    String Docentry;
    String ItemCode;
    String ItemName;
    String WhsCode;
    String Updateby;
    String GhiChu;
    String Sys_Quantity;
    String Mansernum;
    String DistNumber;
    String lineNum;
    String R_Quantity;
    String ShopCode;
 public InventoryResult(){

 }
    public static InventoryResult fromJson(String json) {
        try {
            Gson gson = new Gson();
            //LoginResult result = gson.fromJson(json, LoginResult.class);
            List<InventoryResult> list = gson.fromJson(json, new TypeToken<List<InventoryResult>>() {
            }.getType());
            return list.get(0);
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<InventoryResult> fromJsonArray(String jsonArray) {
        List<InventoryResult> results = new ArrayList<>();
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<InventoryResult>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getDocentry() {
        return Docentry;
    }

    public void setDocentry(String docentry) {
        Docentry = docentry;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getUpdateby() {
        return Updateby;
    }

    public void setUpdateby(String updateby) {
        Updateby = updateby;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getSys_Quantity() {
        return Sys_Quantity;
    }

    public void setSys_Quantity(String sys_Quantity) {
        Sys_Quantity = sys_Quantity;
    }

    public String getMansernum() {
        return Mansernum;
    }

    public void setMansernum(String mansernum) {
        Mansernum = mansernum;
    }

    public String getDistNumber() {
        return DistNumber;
    }

    public void setDistNumber(String distNumber) {
        DistNumber = distNumber;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getR_Quantity() {
        return R_Quantity;
    }

    public void setR_Quantity(String r_Quantity) {
        R_Quantity = r_Quantity;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }
}

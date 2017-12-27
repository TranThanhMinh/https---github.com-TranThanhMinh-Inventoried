package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/27/2017.
 */

public class BarcodeResult implements Serializable {
     String DistNumber;
     int IS_OITW_OSRN;
     String ItemCode;
     String LastPurPrice;
    public String Mansernum;
     String NgayNhap;
     int PriceSys;
     int Sys_Quantity;
     String WhsCode;
     String itemName;
     String GhiChu;
     int lineNum;
     String R_Quantity;
     String Updateby;
     String ShopCode;
     String Docentry;

     public BarcodeResult(){

     }
    public BarcodeResult(String distNumber, int IS_OITW_OSRN, String itemCode, String lastPurPrice, String mansernum, String ngayNhap, int priceSys, int sys_Quantity, String whsCode, String itemName, String ghiChu, int lineNum, String r_Quantity, String updateby, String shopCode, String docentry){
        this.DistNumber = distNumber;
        this.IS_OITW_OSRN = IS_OITW_OSRN;
        this.ItemCode = itemCode;
        this.LastPurPrice = lastPurPrice;
        this.Mansernum = mansernum;
        this.NgayNhap = ngayNhap;
        this.PriceSys = priceSys;
        this.Sys_Quantity = sys_Quantity;
        this.WhsCode = whsCode;
        this.itemName = itemName;
        this.GhiChu = ghiChu;
        this.lineNum = lineNum;
        this.R_Quantity = r_Quantity;
        this.Updateby = updateby;
        this.ShopCode = shopCode;
        this.Docentry = docentry;
    }
    public static BarcodeResult fromJson(String json) {
        try {
            Gson gson = new Gson();
            List<BarcodeResult> list = gson.fromJson(json, new TypeToken<List<BarcodeResult>>() {
            }.getType());
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<BarcodeResult> fromJsonArray(String jsonArray) {
        List<BarcodeResult> results = new ArrayList<>();
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<BarcodeResult>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getDistNumber() {
        return DistNumber;
    }

    public void setDistNumber(String distNumber) {
        DistNumber = distNumber;
    }

    public int getIS_OITW_OSRN() {
        return IS_OITW_OSRN;
    }

    public void setIS_OITW_OSRN(int IS_OITW_OSRN) {
        this.IS_OITW_OSRN = IS_OITW_OSRN;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getLastPurPrice() {
        return LastPurPrice;
    }

    public void setLastPurPrice(String lastPurPrice) {
        LastPurPrice = lastPurPrice;
    }

    public String getMansernum() {
        return Mansernum;
    }

    public void setMansernum(String mansernum) {
        Mansernum = mansernum;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public int getPriceSys() {
        return PriceSys;
    }

    public void setPriceSys(int priceSys) {
        PriceSys = priceSys;
    }

    public int getSys_Quantity() {
        return Sys_Quantity;
    }

    public void setSys_Quantity(int sys_Quantity) {
        Sys_Quantity = sys_Quantity;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getR_Quantity() {
        return R_Quantity;
    }

    public void setR_Quantity(String r_Quantity) {
        R_Quantity = r_Quantity;
    }

    public String getUpdateby() {
        return Updateby;
    }

    public void setUpdateby(String updateby) {
        Updateby = updateby;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    public String getDocentry() {
        return Docentry;
    }

    public void setDocentry(String docentry) {
        Docentry = docentry;
    }
}

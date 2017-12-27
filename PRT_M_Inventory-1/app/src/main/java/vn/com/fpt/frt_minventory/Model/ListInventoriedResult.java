package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ADMIN on 11/30/2017.
 */

public class ListInventoriedResult extends RealmObject implements Serializable {
    @PrimaryKey
    String ItemCode;
    String ItemName;
    int LastPurPrice;
    int LineNum;
    String ManserNum;
    int R_Quantity;
    String Remark;
    String Serial;
    String ShopCode;
    int Sys_Quantiy;
    String UpdateBy;
    String Whs_Code;
    String GhiChu;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    boolean check = false;


    public ListInventoriedResult() {

    }

    public ListInventoriedResult(String itemCode, String itemName, int lastPurPrice, int lineNum, String manserNum, int r_Quantity, String remark, String serial, String shopCode, int sys_Quantiy, String updateBy, String whs_Code) {
        this.ItemCode = itemCode;
        this.ItemName = itemName;
        this.LastPurPrice = lastPurPrice;
        this.LineNum = lineNum;
        this.ManserNum = manserNum;
        this.R_Quantity = r_Quantity;
        this.Remark = remark;
        this.Serial = serial;
        this.ShopCode = shopCode;
        this.Sys_Quantiy = sys_Quantiy;
        this.UpdateBy = updateBy;
        this.Whs_Code = whs_Code;
    }

    public static List<ListInventoriedResult> fromJsonArray(String jsonArray) {
        List<ListInventoriedResult> results = new ArrayList<>();
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<ListInventoriedResult>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
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

    public int getLastPurPrice() {
        return LastPurPrice;
    }

    public void setLastPurPrice(int lastPurPrice) {
        LastPurPrice = lastPurPrice;
    }

    public int getLineNum() {
        return LineNum;
    }

    public void setLineNum(int lineNum) {
        LineNum = lineNum;
    }

    public String getManserNum() {
        return ManserNum;
    }

    public void setManserNum(String manserNum) {
        ManserNum = manserNum;
    }

    public int getR_Quantity() {
        return R_Quantity;
    }

    public void setR_Quantity(int r_Quantity) {
        R_Quantity = r_Quantity;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    public int getSys_Quantiy() {
        return Sys_Quantiy;
    }

    public void setSys_Quantiy(int sys_Quantiy) {
        Sys_Quantiy = sys_Quantiy;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getWhs_Code() {
        return Whs_Code;
    }

    public void setWhs_Code(String whs_Code) {
        Whs_Code = whs_Code;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}

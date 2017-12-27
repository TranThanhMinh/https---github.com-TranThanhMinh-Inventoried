package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by ADMIN on 11/14/2017.
 */

public class PropertiesInventoryOnDay {
    String CreateBy;
    int DocEntry;
    String FromDate;
    String FromTime;
    String LineStatus;
    String LineStatusCode;
    String LoaiSP;
    int LoaiSPCode;
    String LoNguoiKKaiSP;
    String ShopCode;
    String ToDate;
    String ToTime;


    public PropertiesInventoryOnDay(String createBy, int docEntry, String fromDate, String fromTime, String lineStatus, String lineStatusCode, String loaiSP, int loaiSPCode, String loNguoiKKaiSP, String shopCode, String toDate, String toTime ) {
        this.DocEntry = docEntry;
        this.CreateBy = createBy;
        this.FromDate = fromDate;
        this.FromTime = fromTime;
        this.LineStatus = lineStatus;
        this.LineStatusCode = lineStatusCode;
        this.LoaiSP =  loaiSP;
        this.LoaiSPCode = loaiSPCode;
        this.LoNguoiKKaiSP = loNguoiKKaiSP;
        this.ShopCode = shopCode;
        this.ToDate = toDate;
        this.ToTime = toTime;
    }
public  PropertiesInventoryOnDay(){}
    public static List<PropertiesInventoryOnDay> fromJsonArray(String jsonArray) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<PropertiesInventoryOnDay>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public int getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(int docEntry) {
        DocEntry = docEntry;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getLineStatus() {
        return LineStatus;
    }

    public void setLineStatus(String lineStatus) {
        LineStatus = lineStatus;
    }

    public String getLineStatusCode() {
        return LineStatusCode;
    }

    public void setLineStatusCode(String lineStatusCode) {
        LineStatusCode = lineStatusCode;
    }

    public String getLoaiSP() {
        return LoaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        LoaiSP = loaiSP;
    }

    public int getLoaiSPCode() {
        return LoaiSPCode;
    }

    public void setLoaiSPCode(int loaiSPCode) {
        LoaiSPCode = loaiSPCode;
    }

    public String getLoNguoiKKaiSP() {
        return LoNguoiKKaiSP;
    }

    public void setLoNguoiKKaiSP(String loNguoiKKaiSP) {
        LoNguoiKKaiSP = loNguoiKKaiSP;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }
}


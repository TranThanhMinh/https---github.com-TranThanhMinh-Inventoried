package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhtran on 12/26/17.
 */

public class Job {

    @SerializedName("CreateBy")
    @Expose
    private String createBy;
    @SerializedName("DocEntry")
    @Expose
    private Integer docEntry;
    @SerializedName("DocStatus")
    @Expose
    private String docStatus;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("LoaiSP")
    @Expose
    private String loaiSP;
    @SerializedName("LoaiSPCode")
    @Expose
    private Integer loaiSPCode;
    @SerializedName("ShopCode")
    @Expose
    private String shopCode;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("ToTime")
    @Expose
    private String toTime;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public Integer getLoaiSPCode() {
        return loaiSPCode;
    }

    public void setLoaiSPCode(Integer loaiSPCode) {
        this.loaiSPCode = loaiSPCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}

package vn.com.fpt.frt_minventory.Model;

/**
 * Created by ADMIN on 12/19/2017.
 */

public class upload {
    String DocEntry;
    String ItemCode;
    String ImageName;
    String Serial;
    String FileByte;
    String DeviceType;


    String FromDate;
    String ToDate;
    String SoPhieu;

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getSoPhieu() {
        return SoPhieu;
    }

    public void setSoPhieu(String soPhieu) {
        SoPhieu = soPhieu;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    String ShopCode;

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }
    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getFileByte() {
        return FileByte;
    }

    public void setFileByte(String fileByte) {
        FileByte = fileByte;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }


}

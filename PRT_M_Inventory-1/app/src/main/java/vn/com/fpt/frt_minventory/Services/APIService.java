package vn.com.fpt.frt_minventory.Services;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vn.com.fpt.frt_minventory.Adapter.Notify;
import vn.com.fpt.frt_minventory.Libs.HTTPHelper;
import vn.com.fpt.frt_minventory.Model.Area_CalendarResult;
import vn.com.fpt.frt_minventory.Model.BarcodeResult;
import vn.com.fpt.frt_minventory.Model.DataUserLogin;
import vn.com.fpt.frt_minventory.Model.InventoriedListResult;
import vn.com.fpt.frt_minventory.Model.ListInventoriedResult;
import vn.com.fpt.frt_minventory.Model.LoginResult;
import vn.com.fpt.frt_minventory.Model.PropertiesArea_Calendar;
import vn.com.fpt.frt_minventory.Model.PropertiesInventoryOnDay;
import vn.com.fpt.frt_minventory.Model.PropertiesWarehouseCode;
import vn.com.fpt.frt_minventory.Model.UnlockInventoryResult;
import vn.com.fpt.frt_minventory.Views.LoginActivity;

/**
 * Created by ADMIN on 11/20/2017.
 */

public class APIService {
    public static final String INVENTORY_SERVICE_API = "http://118.69.201.43:1010/Service.svc";
    public static Context context;

    public APIService(Context context) {
        this.context = context;
    }

    //    Login
    public static LoginResult getLogin(String UserName, String Password) {
        try {
            JSONObject params = new JSONObject();
            //            gia tri truyen len service
            params.accumulate("UserName", UserName);
            params.accumulate("Password", Password);
            params.accumulate("KeyNum", "");
            params.accumulate("IP", "");
            String response = HTTPHelper.makeHTTPRequest("POST", INVENTORY_SERVICE_API + "/KiemKeLogin",
                    params, null);
            JSONObject root = new JSONObject(response);

            return LoginResult.fromJson(root.getString("InvetoryLogin"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    Set barcode
    public static List<BarcodeResult> getInventoryDetail(String Itemcode_serial, String WhsCode, int LoaiSPCode) {
        try {
            JSONObject params = new JSONObject();
            //            gia tri truyen len service
            params.accumulate("ShopCode", DataUserLogin.Shopcode);
            params.accumulate("Itemcode_serial", Itemcode_serial);
            params.accumulate("WhsCode", WhsCode);
            params.accumulate("Type", LoaiSPCode);
            params.accumulate("isMobile", 1);
            String response = HTTPHelper.makeHTTPRequest("POST", INVENTORY_SERVICE_API + "/CheckInvetoryKiemKe", params, null);
            JSONObject root = new JSONObject(response);
            return BarcodeResult.fromJsonArray(root.getString("CheckInvetory"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hoan tat dat lich kiem ke
    public static Area_CalendarResult getFinishCalendar(String ListShop, String SoLuongTaoCT, String FromDate, String ToDate, String FromTime, String ToTime, String LoaiSP) {
        try {
            JSONObject params = new JSONObject();
//            gia tri truyen len service
            params.accumulate("ListShop", ListShop);
            params.accumulate("LoaiKiemKe", true);
            params.accumulate("SoLuongTaoCT", SoLuongTaoCT);
            params.accumulate("FromDate", FromDate);
            params.accumulate("ToDate", ToDate);
            params.accumulate("FromTime", FromTime);
            params.accumulate("ToTime", ToTime);
            params.accumulate("CreateBy", DataUserLogin.Username);
            params.accumulate("ChucDanh", DataUserLogin.EmployeeName);
            params.accumulate("LoaiSP", LoaiSP);
            
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetoryCreateJob", params, null
            );
            JSONObject root = new JSONObject(response);
            return Area_CalendarResult.fromJson(root.getString("CreateJob"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    Danh sach kiem ke trong ngay
    public static List<PropertiesInventoryOnDay> getInventoryOnDay() {
        try {
            JSONObject params = new JSONObject();
            //            gia tri truyen len service
            params.accumulate("Shopcode", DataUserLogin.Shopcode);
            String response = HTTPHelper.makeHTTPRequest("POST", INVENTORY_SERVICE_API + "/SearchKiemKeTrongNgay", params, null);
            JSONObject root = new JSONObject(response);
            return PropertiesInventoryOnDay.fromJsonArray(root.getString("SearchTrongNgay"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    Select Ma Kho
    public static List<PropertiesWarehouseCode> getSelectWarehouseCodes() {
        try {
            JSONObject params = new JSONObject();
//            gia tri truyen len service
            params.accumulate("Shopcode", DataUserLogin.Shopcode);
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetorySearchMaKho", params, null
            );
            JSONObject root = new JSONObject(response);
            return PropertiesWarehouseCode.fromJsonArray(root.getString("SearchMaKho"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    Select Vung
    public static List<PropertiesArea_Calendar> getPropertiesArea_calendars() {
        try {
            JSONObject params = new JSONObject();
//            gia tri truyen len service
            params.accumulate("userCode", DataUserLogin.Username);
            params.accumulate("CodeCountry", "");
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetoryGetShopByUser", params, null
            );
            JSONObject root = new JSONObject(response);
            return PropertiesArea_Calendar.fromJsonArray(root.getString("ShopByUser")); // tên trong bản postman trả về
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //    Luu danh sach da check barcode
    public static Notify saveSelectWarehouseCodes(String table) {
        try {
            JSONObject params = new JSONObject();
//            gia tri truyen len service
            params.accumulate("table", table);
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetoryInsertDetailKiemKe", params, null
            );
            JSONObject root = new JSONObject(response);
            return Notify.fromJson(root.getString("DetailKiemKe"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
    // tim phieu kiem ke theo lich
    public static List<InventoriedListResult> getInventoriedListResult(String FromDate, String ToDate, String SoPhieu) {
        try {
            JSONObject params = new JSONObject();
            // gia tri tuyen len
            params.accumulate("FromDate", FromDate);
            params.accumulate("ToDate", ToDate);
            params.accumulate("SoPhieu", SoPhieu);
            params.accumulate("ShopCode", DataUserLogin.Shopcode);

            String reponse = HTTPHelper.makeHTTPRequest("POST", INVENTORY_SERVICE_API + "/SearchDanhSachKiemKe", params, null);
            JSONObject root = new JSONObject(reponse);
            return InventoriedListResult.fromJsonArray(root.getString("SearchListKiemKe"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

            //  Mo khoa phieu
    public static UnlockInventoryResult getUnlockInventoryResult(String DocEntry){
        try {
            JSONObject params = new JSONObject();
            // gia tri tuyen len
            params.accumulate("KeyNum", "0");
            params.accumulate("User", DataUserLogin.Username);
            params.accumulate("ObjType", "10");
            params.accumulate("Value", DocEntry);
            params.accumulate("Mode", "D");
            params.accumulate("serial", "");

            String reponse = HTTPHelper.makeHTTPRequest("POST", INVENTORY_SERVICE_API + "/InvetoryTransactionLog", params, null);
            JSONObject root = new JSONObject(reponse);
            return UnlockInventoryResult.fromJson(root.getString("transactionlog"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //    Xem danh sach da Kiem ke
    public static List<ListInventoriedResult> getListInventoried(String Docentry) {
        try {
            JSONObject params = new JSONObject();
//            gia tri truyen len service
            params.accumulate("Docentry", Docentry);
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetoryDetailKK", params, null
            );
            JSONObject root = new JSONObject(response);
            return ListInventoriedResult.fromJsonArray(root.getString("DetailKK"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //  Ket thuc kiem ke
    public static String getKetThucKK(String DocEntry, String docstatus, String updateby,String image) {

        try {
            JSONObject params = new JSONObject();
            Log.e("DocEntry",DocEntry);
            Log.e("docstatus",docstatus);
            Log.e("updateby",LoginActivity.user + " - " + LoginActivity.userName);
            Log.e("nguoikk",DataUserLogin.Username + " - " + DataUserLogin.EmployeeName);
            Log.e("xml",image);

//            gia tri truyen len service
            params.accumulate("DocEntry", DocEntry);
            params.accumulate("docstatus", docstatus);
            params.accumulate("updateby", LoginActivity.user + " - " + LoginActivity.userName);
            params.accumulate("nguoikk", DataUserLogin.Username + " - " + DataUserLogin.EmployeeName);
            params.accumulate("type", 0);
            params.accumulate("isMobile", 1);
            params.accumulate("typeOS", 1);
            params.accumulate("xml", image);
            String response = HTTPHelper.makeHTTPRequest(
                    "POST", INVENTORY_SERVICE_API + "/InvetoryHangHoaFinish", params, null
            );
            //JSONObject root = new JSONObject(response);
            return response;
            //return Notify.fromJson(root.getString("DetailKK"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


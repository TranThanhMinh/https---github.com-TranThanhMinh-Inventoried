package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/22/2017.
 */

public class LoginResult implements Serializable {
    public String AvatarImageLink;
    public String CashAccount;
    public String CompanyAddress;
    public String CompanyCode;
    public String CompanyCodeB1;
    public String CompanyName;
    public String CompanyPhone;
    public String CompanyTaxCode;
    public String DateTime_Log;
    public String ERR;
    public String EmployeeName;
    public String ID;
    public String IsShop;
    public int Is_PrintType;
    public String JobTitle;
    public String JobTitle_name;
    public String PriceName;
    public int Result;
    public String ShopCode;
    public String ShopName;
    public String U_SH_TYPE;
    public String UserName;
    public String WarningSO;
    public int is_auto_print;

    public static LoginResult fromJson(String json) {
        try {
            Gson gson = new Gson();
            //LoginResult result = gson.fromJson(json, LoginResult.class);
            List<LoginResult> list= gson.fromJson(json, new TypeToken<List<LoginResult>>() {
            }.getType());
            return list.get(0);
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<LoginResult> fromJsonArray(String jsonArray) {
        List<LoginResult> results = new ArrayList<>();
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<LoginResult>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    public String getAvatarImageLink() {
        return AvatarImageLink;
    }

    public void setAvatarImageLink(String avatarImageLink) {
        AvatarImageLink = avatarImageLink;
    }

    public String getCashAccount() {
        return CashAccount;
    }

    public void setCashAccount(String cashAccount) {
        CashAccount = cashAccount;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getCompanyCodeB1() {
        return CompanyCodeB1;
    }

    public void setCompanyCodeB1(String companyCodeB1) {
        CompanyCodeB1 = companyCodeB1;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyPhone() {
        return CompanyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        CompanyPhone = companyPhone;
    }

    public String getCompanyTaxCode() {
        return CompanyTaxCode;
    }

    public void setCompanyTaxCode(String companyTaxCode) {
        CompanyTaxCode = companyTaxCode;
    }

    public String getDateTime_Log() {
        return DateTime_Log;
    }

    public void setDateTime_Log(String dateTime_Log) {
        DateTime_Log = dateTime_Log;
    }

    public String getERR() {
        return ERR;
    }

    public void setERR(String ERR) {
        this.ERR = ERR;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIsShop() {
        return IsShop;
    }

    public void setIsShop(String isShop) {
        IsShop = isShop;
    }

    public int getIs_PrintType() {
        return Is_PrintType;
    }

    public void setIs_PrintType(int is_PrintType) {
        Is_PrintType = is_PrintType;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getPriceName() {
        return PriceName;
    }

    public void setPriceName(String priceName) {
        PriceName = priceName;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String shopCode) {
        ShopCode = shopCode;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getU_SH_TYPE() {
        return U_SH_TYPE;
    }

    public void setU_SH_TYPE(String u_SH_TYPE) {
        U_SH_TYPE = u_SH_TYPE;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getWarningSO() {
        return WarningSO;
    }

    public void setWarningSO(String warningSO) {
        WarningSO = warningSO;
    }

    public int getIs_auto_print() {
        return is_auto_print;
    }

    public void setIs_auto_print(int is_auto_print) {
        this.is_auto_print = is_auto_print;
    }

    public String getJobTitle_name() {
        return JobTitle_name;
    }

    public void setJobTitle_name(String jobTitle_name) {
        JobTitle_name = jobTitle_name;
    }
}


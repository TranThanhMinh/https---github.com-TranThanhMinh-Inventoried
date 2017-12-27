package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by ADMIN on 12/12/2017.
 */

public class PropertiesArea_Calendar {
    String ID;
    String NAME;
    String RootID;
    boolean check = false;

    public PropertiesArea_Calendar(String _ID, String _NAME, String rootID) {
      this.ID = _ID;
      this.NAME = _NAME;
      this.RootID = rootID;
    }

    public PropertiesArea_Calendar() {
    }

    public static List<PropertiesArea_Calendar> fromJsonArray(String jsonArray) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<PropertiesArea_Calendar>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getRootID() {
        return RootID;
    }

    public void setRootID(String rootID) {
        RootID = rootID;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

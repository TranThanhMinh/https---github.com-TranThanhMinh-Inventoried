package vn.com.fpt.frt_minventory.Libs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/20/2017.
 */

public class CheckVersion implements Serializable {
    private String messagess;
    private int p_status;

    public CheckVersion(String messagess, int p_status) {
        this.messagess = messagess;
        this.p_status = p_status;
    }

    public CheckVersion() {

    }

    public static CheckVersion fromJson(String json) {
        try {
            Gson gson = new Gson();
            CheckVersion result = gson.fromJson(json, CheckVersion.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> ArrayList<T> fromJsonArray(String jsonArray) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessagess() {
        return messagess;
    }

    public void setMessagess(String messagess) {
        this.messagess = messagess;
    }

    public int getP_status() {
        return p_status;
    }

    public void setP_status(int p_status) {
        this.p_status = p_status;
    }
}

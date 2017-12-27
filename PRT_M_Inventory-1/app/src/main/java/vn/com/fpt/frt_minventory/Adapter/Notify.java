package vn.com.fpt.frt_minventory.Adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;


/**
 * Created by ADMIN on 11/30/2017.
 */

public class Notify implements Serializable {
    String Message;
    int Result;

public Notify(){

}
    public static Notify fromJson(String jsonArray) {
        try {
            Gson gson = new Gson();
            List<Notify> list = gson.fromJson(jsonArray, new TypeToken<List<Notify>>() {
            }.getType());
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}

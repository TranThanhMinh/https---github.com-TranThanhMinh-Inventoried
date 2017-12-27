package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ADMIN on 12/22/2017.
 */

public class UnlockInventoryResult implements Serializable {
    String Result;

    public UnlockInventoryResult() {

    }

    public UnlockInventoryResult(String result, String u_Desc) {
        this.Result = result;
    }

    public static UnlockInventoryResult fromJson(String json) {
        try {
            Gson gson = new Gson();
            List<UnlockInventoryResult> list = gson.fromJson(json, new TypeToken<List<UnlockInventoryResult>>() {

            }.getType());
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}

package vn.com.fpt.frt_minventory.Model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ADMIN on 12/19/2017.
 */

public class InsertImage implements Serializable {
    @SerializedName("DeviceType")
    @Expose
    private Integer deviceType;
    @SerializedName("Docentry")
    @Expose
    private Integer docentry;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("Result")
    @Expose
    private Integer result;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getDocentry() {
        return docentry;
    }

    public void setDocentry(Integer docentry) {
        this.docentry = docentry;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
    public static InsertImage fromJson(String json) {
        try {
            Gson gson = new Gson();
            //LoginResult result = gson.fromJson(json, LoginResult.class);
            List<InsertImage> list= gson.fromJson(json, new TypeToken<List<InsertImage>>() {
            }.getType());
            return list.get(0);
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package vn.com.fpt.frt_minventory.Model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/7/2017.
 */

public class CameraResult implements Serializable {
 Bitmap gallery;


    public static List<CameraResult> fromJsonArray(String jsonArray) {
        List<CameraResult> results = new ArrayList<>();
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonArray, new TypeToken<List<CameraResult>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
 public CameraResult (){

 }

 public CameraResult(Bitmap gallery){
     this.gallery = gallery;
 }

    public Bitmap getGallery() {
        return gallery;
    }

    public void setGallery(Bitmap gallery) {
        this.gallery = gallery;
    }
}


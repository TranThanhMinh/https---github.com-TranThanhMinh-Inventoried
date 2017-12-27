package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 12/19/2017.
 */

public class Image {
    @SerializedName("InsertImage")
    @Expose
    private List<InsertImage> insertImage = null;

    public List<InsertImage> getInsertImage() {
        return insertImage;
    }

    public void setInsertImage(List<InsertImage> insertImage) {
        this.insertImage = insertImage;
    }
}

package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by minhtran on 12/23/17.
 */

public class SearchImage {
    @SerializedName("SearchImage")
    @Expose
    private List<ViewImage> searchImage = null;

    public List<ViewImage> getSearchImage() {
        return searchImage;
    }

    public void setSearchImage(List<ViewImage> searchImage) {
        this.searchImage = searchImage;
    }
}

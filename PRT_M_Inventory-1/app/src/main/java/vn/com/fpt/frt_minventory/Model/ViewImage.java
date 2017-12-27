package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhtran on 12/23/17.
 */

public class ViewImage {

    @SerializedName("Docentry")
    @Expose
    private Integer docentry;
    @SerializedName("Domain")
    @Expose
    private String domain;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("Serial")
    @Expose
    private String serial;

    public Integer getDocentry() {
        return docentry;
    }

    public void setDocentry(Integer docentry) {
        this.docentry = docentry;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

}

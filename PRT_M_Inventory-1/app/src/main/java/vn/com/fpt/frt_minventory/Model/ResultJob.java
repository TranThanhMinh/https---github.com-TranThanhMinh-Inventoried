package vn.com.fpt.frt_minventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by minhtran on 12/26/17.
 */

public class ResultJob {
    @SerializedName("SearchJob")
    @Expose
    private List<Job> searchJob = null;

    public List<Job> getSearchJob() {
        return searchJob;
    }

    public void setSearchJob(List<Job> searchJob) {
        this.searchJob = searchJob;
    }
}

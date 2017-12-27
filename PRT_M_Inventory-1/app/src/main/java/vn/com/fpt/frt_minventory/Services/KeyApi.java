package vn.com.fpt.frt_minventory.Services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.com.fpt.frt_minventory.Model.Downloadimage;
import vn.com.fpt.frt_minventory.Model.Image;
import vn.com.fpt.frt_minventory.Model.ResultJob;
import vn.com.fpt.frt_minventory.Model.SearchImage;
import vn.com.fpt.frt_minventory.Model.upload;

/**
 * Created by ADMIN on 12/19/2017.
 */

public interface KeyApi {
    @POST("InvetoryUploadImage")
    Call<Image> InvetoryUploadImage(@Body upload up);

    @POST("InvetorySearchImage")
    Call<SearchImage> InvetorySearchImage(@Body Downloadimage up);

    @POST("InvetorySearchJob")
    Call<ResultJob> InvetorySearchJob(@Body upload up);

}
package com.example.madcamp_week2.Frag2;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("/load")
    Call<List<Gets>> getImageUrl();

    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(
            @Part MultipartBody.Part image,
            @Part("upload") RequestBody name
    );

    @Multipart
    @POST("/upload/multi")
    Call<ResponseBody> postMultiImage(
            @Part List<MultipartBody.Part> files,
            @Part("upload") RequestBody name
    );

    @POST("/del")
    Call<ResponseBody> delImage(@Body String location);

}

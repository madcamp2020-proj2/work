package com.example.madcamp_week2.Frag1;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactService {

    @POST("/post")
    Call<ResponseBody> postContact(
            @Body ArrayList<Contacts> contactsData
    );

}

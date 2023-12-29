package com.example.ui_qa_patrol.models.jwt;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APICall {
//    URL ARTEMIS


    // -----------------------------------------------------------------
    @GET(".")
    Call<ResponseBody> isReachable();


    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseBody> loginApps(@Field("email") String email, @Field("password") String password);

    @GET("api/refresh")
    Call<ResponseBody> refreshToken(@Header("Content-Type") String contentType, @Header("Authorization") String auth);

    @GET("api/user-profile/{nik}")
    Call<ResponseBody> userProfile(@Header("Content-Type") String contentType, @Header("Authorization") String auth, @Path(value = "nik", encoded = true) String nik);

    @GET("api/logout")
    Call<ResponseBody> logOutApps(@Header("Content-Type") String contentType, @Header("Authorization") String auth);

//    @POST("api/user-login")
//    Call<ResponseBody> userLogin(@Header("Content-Type") String contentType, @Header("Authorization") String auth, @Body Login login);

//    @GET("api/user")
//    Call<ResponseBody> user(@Header("Content-Type") String contentType, @Header("Authorization") String auth);
}

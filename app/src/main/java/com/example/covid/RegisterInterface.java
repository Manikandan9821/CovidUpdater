package com.example.covid;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface
{
    //https://fluttercurd.000webhostapp.com/covid/register.php
    String REGIURL = "https://fluttercurd.000webhostapp.com/covid/";
    @FormUrlEncoded
    @POST("register.php")
    Call<String> getUserRegi(
            @Field("username") String uname,
            @Field("email") String email,
            @Field("password") String password
    );
}

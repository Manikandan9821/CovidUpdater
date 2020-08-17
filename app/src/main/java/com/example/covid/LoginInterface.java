package com.example.covid;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface
{
    //https://fluttercurd.000webhostapp.com/covid/login.php

    String LOGINURL = "https://fluttercurd.000webhostapp.com/covid/";
    @FormUrlEncoded
    @POST("login.php")
    Call<String> getUserLogin(
            @Field("email") String email,
            @Field("password") String password
    );

}

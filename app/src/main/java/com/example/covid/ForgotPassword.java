package com.example.covid;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ForgotPassword
{
   //https://fluttercurd.000webhostapp.com/covid/forgotpassword.php

    String FORGOTURL = "https://fluttercurd.000webhostapp.com/covid/";
    @FormUrlEncoded
    @POST("forgotpassword.php")
    Call<String> getUserForgotPassword(
            @Field("email") String email,
            @Field("password") String password
    );
}

package com.example.covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public  interface ApiInterface
{
    String JSONURL = "https://disease.sh/v2/";

    //https://disease.sh/v2/countries



    @GET("all")
    Call<ApiModel> getData();


    @GET("all")
    Call<String> getdata();

    @GET("all")
    Call<Double> getdat();

    @GET("all")
    Call<List<ApiModel>> getDaTa();

     @GET("all")
    Call<Integer> getInTa();
}

package com.example.covid;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesInterface
{

    String JSONURL = "https://disease.sh/v2/";

    @GET("countries")
    Call<String> getString();
}

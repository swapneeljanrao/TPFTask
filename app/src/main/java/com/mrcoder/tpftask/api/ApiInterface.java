package com.mrcoder.tpftask.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<com.mrcoder.tpftask.activity.model.Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<com.mrcoder.tpftask.activity.model.Headlines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}

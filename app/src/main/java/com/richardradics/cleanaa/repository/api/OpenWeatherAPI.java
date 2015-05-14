package com.richardradics.cleanaa.repository.api;

import com.richardradics.cleanaa.repository.api.model.openweatherwrapper.OpenWeatherWrapper;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
public interface OpenWeatherAPI {

    @GET("/data/2.5/find")
    void getWeatherItems(@Query("lat") Double latitude, @Query("lon") Double longitude, @Query("cnt") Integer count, Callback<OpenWeatherWrapper> callback);

    @GET("/data/2.5/find")
    OpenWeatherWrapper getWeatherItems(@Query("lat") Double latitude, @Query("lon") Double longitude, @Query("cnt") Integer count);

}

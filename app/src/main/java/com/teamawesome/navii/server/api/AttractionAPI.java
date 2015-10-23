package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Attraction;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface AttractionAPI {

    @POST("/attraction")
    public Call<Attraction> createAttraction(@Body Attraction attraction);
}

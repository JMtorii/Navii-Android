package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Attraction;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface AttractionAPI {

    @POST("/attraction")
    Call<Attraction> createAttraction(@Body Attraction attraction);

    @GET("/attraction/getYelpImage/{attractionName}/{attractionCll}/")
    Call<Attraction> getYelpImageUrl(@Path("attractionName") String name, @Path("attractionCll") String cll);
}

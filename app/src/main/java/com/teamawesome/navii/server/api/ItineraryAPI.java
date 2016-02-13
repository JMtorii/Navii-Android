package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface ItineraryAPI {
    /**
     * Get itineraries based on tags pressed
     * @param  tags The List of tags user has selected
     */
    @POST("/itinerary/tags")
    Call<List<Itinerary>> getItineraries(@Body List<String> tags);
}

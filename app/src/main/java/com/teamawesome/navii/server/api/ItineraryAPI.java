package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Itinerary;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface ItineraryAPI {
    /**
     * Get itineraries based on tags pressed
     * @param  tags The Array of tags user has selected
     */
    @GET("/itinerary/tags/{tag_list}/{num_days}")
    Observable<List<Itinerary>> getItineraries(@Path("tag_list") String tags, @Path("num_days") int days);
}

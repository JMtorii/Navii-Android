package com.teamawesome.navii.server.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by sjung on 03/12/15.
 */
public interface TagsAPI {

    /**
     * Returns a list of 20 random tags from the server
     * @return list of random tags from the server
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/tags")
    Observable<List<String>> getTags();
}

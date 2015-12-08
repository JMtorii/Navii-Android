package com.teamawesome.navii.server.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by sjung on 03/12/15.
 */
public interface TagsAPI {

    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/tags")
    Observable<List<String>> getTags();
}

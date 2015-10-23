package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface UserAPI {

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") int userId);

    @Headers({
            "Content-Type: application/json",
            "Cache-Control: no-cache"
    })
    @POST("/user/")
    Call<User> createUser(@Body User user);
}

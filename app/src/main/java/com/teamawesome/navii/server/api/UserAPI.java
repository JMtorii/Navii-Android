package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by JMtorii on 2015-10-21.
 */
public interface UserAPI {

    /**
     * Creates a new user
     * @param user  User model
     * @return      
     */
    @Headers({
            "Content-Type: application/json",
            "Cache-Control: no-cache"
    })
    @POST("/user/")
    Call<User> createUser(@Body User user);
}

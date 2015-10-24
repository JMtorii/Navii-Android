package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.User;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by JMtorii on 2015-10-21.
 */
// TODO: are the headers necessary?
public interface UserAPI {
    /**
     * Gets an existing user
     * @param userId  Identifier for user
     * @return        User fetched.
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/user/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    /**
     * Gets all existing users
     * @return      A list of users
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/user")
    Call<ArrayList<User>> getAllUsers();

    /**
     * Creates a new user
     * @param user  User model
     * @return      Created user
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @POST("/user")
    Call<User> createUser(@Body User user);

    /**
     * Updates an existing user
     * @return  Updated user
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @PUT("/user/{userId}")
    Call<User> updateUser(@Path("userId") String userId, @Body User user);

    /**
     * Deletes an existing user
     * @param userId    The identifier of the user to delete
     * @return          The deleted user
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @DELETE("/user/{userId}")
    Call<User> deleteUser(@Path("userId") String userId);
}

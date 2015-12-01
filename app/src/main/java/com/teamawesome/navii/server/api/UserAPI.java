package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

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
    @GET("/user/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    /**
     * Gets all existing users
     * @return      A list of users
     */
    @GET("/user")
    Call<List<User>> getAllUsers();

    /**
     * Creates a new user
     * @param user  User model
     * @return      Created user
     */
    @Headers({"Content-Type: application/json"})
    @POST("/user")
    Call<String> createUser(@Body User user);

    /**
     * Updates an existing user
     * @return  Updated user
     *
     * TODO: Fix this in the server and update here. This does not work yet.
     */
    @Headers({"Content-Type: application/json"})
    @PUT("/user/{userId}")
    Call<?> updateUser(@Path("userId") String userId, @Body User user);

    /**
     * Deletes an existing user
     * @param userId    The identifier of the user to delete
     * @return          The deleted user
     */
    @DELETE("/user/{userId}")
    Call<?> deleteUser(@Path("userId") String userId);

    /**
     * Deletes all users
     * @return          The number of deleted users
     */
    @DELETE("/user")
    Call<Integer> deleteAll();

    /**
     * Signs ups the user into the database
     * @param username    The username of the user
     * @param password    The password of the user
     */
    @POST("/user/signUp")
    Call<?> signUp(@Query("username") String username, @Query("password") String password);

    /**
     * Login the user
     * @param username    The username of the user
     * @param password    The password of the user
     */
    @GET("/user/login")
    Call<?> login(@Query("username") String username, @Query("password") String password);
}

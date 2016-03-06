package com.teamawesome.navii.server.api;

import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.server.model.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by JMtorii on 2015-10-21.
 */
// TODO: are the headers necessary?
public interface UserAPI {
    /**
     * Gets an existing user
     *
     * @param username Username of the user
     * @return User fetched.
     */
    @GET("/user/{username}")
    Call<User> getUser(@Path("username") String username);

    /**
     * Creates a new user
     *
     * @param user User model
     * @return Number of created users
     */
    @Headers({"Content-Type: application/json"})
    @POST("/user")
    Call<Void> createUser(@Body User user);

    /**
     * Changes the user's password after validating
     *
     * @return Returns number of updated users wrapped in an Observable
     */
    @PUT("/user/changePassword")
    Observable<Void> changePassword(@Query("username") String username, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /**
     * Updates an existing user
     *
     * @param user        The current user
     * @param newUsername The new username
     * @return Updated user
     * <p/>
     * TODO: Fix this in the server and update here. This does not work yet.
     */
    @Headers({"Content-Type: application/json"})
    @PUT("/user/update")
    Call<ResponseBody> updateUser(@Body User user, @Query("newUsername") String newUsername);

    /**
     * Signs ups the user into the database
     *
     * @param username The username of the user
     * @param password The password of the user
     */
    @POST("/user/signUp")
    Call<Void> signUp(@Query("username") String username, @Query("password") String password);

    /**
     * Login the user
     *
     * @param username The username of the user
     * @param password The password of the user
     */
    @GET("/user/login")
    Call<Void> login(@Query("username") String username, @Query("password") String password);
}

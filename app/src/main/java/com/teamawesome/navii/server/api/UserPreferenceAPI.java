package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.UserPreference;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by sjung on 16/11/15.
 */
public interface UserPreferenceAPI {


    /**
     * Creates a new user
     * @param user  User model
     * @return      Created user
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @POST("/userpreference")
    Call<UserPreference> createUserPreference(@Body UserPreference user);

    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/userpreference/{userId}")
    Call<ArrayList<Preferences>> getAllUserPreferences(@Path("userId") String userId);

    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @DELETE("/userpreference/{userId}")
    Call<UserPreference> deleteAllUserPreference(@Path("userId") String userId);
}

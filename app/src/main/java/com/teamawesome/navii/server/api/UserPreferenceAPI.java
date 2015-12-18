package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.UserPreference;

import java.util.List;
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
     * Creates a new userpreference
     *
     * @param userPreference UserPreference model
     * @return Created UserPreference
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @POST("/userpreference")
    Call<Void> createUserPreference(@Body UserPreference userPreference);

    /**
     * Gets
     * @param username UserId
     * @return
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @GET("/userpreference/{userId}")
    Call<List<Preferences>> getAllUserPreferences(@Path("userId") String username);

    /**
     *
     * @param username
     * @param preferenceType
     * @return
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @DELETE("/userpreference/{username}/{preferenceType}")
    Call<Void> deleteAllUserPreference(@Path("username") String username, @Path("preferenceType") int preferenceType);
}

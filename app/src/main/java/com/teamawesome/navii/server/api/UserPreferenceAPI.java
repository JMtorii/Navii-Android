package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Preference;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by sjung on 16/11/15.
 */
public interface UserPreferenceAPI {

    /**
     * Creates a new userpreference
     *
     * @param preferences Preference model
     * @return Created Preference
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @POST("/userpreference/create")
    Call<Void> createUserPreference(@Body List<Preference> preferences);

    /**
     * Deletes a specific type of preferences
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Cache-Control: no-cache"})
    @DELETE("/userpreference/delete/{preferenceType}")
    Call<Void> deleteAllUserPreference();
}

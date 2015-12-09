package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.Preference;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by sjung on 08/12/15.
 */
public interface PreferenceAPI {
    /**
     * Returns a list of preferences from the server based on type specified
     * @param preferenceType type of preference based on question
     * @return list of preferences from the server
     */
    @Headers({"Content-Type: application/json"})
    @GET("/preference/{preferenceType}")
    Call<List<Preference>> getPreferences(@Path("preferenceType") int preferenceType);
}

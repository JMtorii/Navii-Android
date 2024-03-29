package com.teamawesome.navii.server.api;

import com.teamawesome.navii.server.model.PreferencesQuestion;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by sjung on 08/12/15.
 */
public interface PreferenceAPI {
    /**
     * Returns a list of preferences and the question from the server based on type specified
     *
     * @param preferenceType type of preference based on question
     * @return list of preferences from the server
     */
    @Headers({"Content-Type: application/json"})
    @GET("/preference/{preferenceType}")
    Observable<PreferencesQuestion> getPreferences(@Path("preferenceType") int preferenceType);
}

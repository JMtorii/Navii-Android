package com.teamawesome.navii.server.api;

import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.server.model.User;
import com.teamawesome.navii.server.model.VoyagerResponse;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by ecrothers on 07/31/16.
 */
public interface LoginAPI {
    @POST("/login")
    Observable<VoyagerResponse> attemptLogin(@Body User user);

    @POST("/login/fb/{accessToken}")
    Observable<ResponseBody> attemptFacebookLogin(@Path("accessToken") String accessToken);
}

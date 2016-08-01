package com.teamawesome.navii.server.api;

import com.squareup.okhttp.ResponseBody;
import com.teamawesome.navii.server.model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ecrothers on 07/31/16.
 */
public interface LoginAPI {
    @POST("/login")
    Call<ResponseBody> attemptLogin(@Body User user);

    @POST("/login/fb/{accessToken}")
    Call<ResponseBody> attemptFacebookLogin(@Path("accessToken") String accessToken);
}

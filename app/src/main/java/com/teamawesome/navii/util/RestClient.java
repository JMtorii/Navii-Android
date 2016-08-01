package com.teamawesome.navii.util;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.teamawesome.navii.server.api.AttractionAPI;
import com.teamawesome.navii.server.api.ItineraryAPI;
import com.teamawesome.navii.server.api.LoginAPI;
import com.teamawesome.navii.server.api.PreferenceAPI;
import com.teamawesome.navii.server.api.TagsAPI;
import com.teamawesome.navii.server.api.UserAPI;
import com.teamawesome.navii.server.api.UserPreferenceAPI;

import java.io.IOException;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by ecrothers on 07/31/16.
 */
public class RestClient {
    private static Retrofit retrofitReactiveClient;

    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (SessionManager.checkLogin()) {
                request = request.newBuilder()
                        .addHeader("X-AUTH-TOKEN", SessionManager.getToken())
                        .addHeader("Accept", "*/*")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Content-Type", "application/json")
                        .build();
            } else {
                request = request.newBuilder()
                        .addHeader("Accept", "*/*")
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Content-Type", "application/json")
                        .build();
            }
            Response response = chain.proceed(request);
            return response;
        }
    }

    private static HeaderInterceptor headerInterceptor;
    public static UserAPI userAPI;
    public static UserPreferenceAPI userPreferenceAPI;
    public static PreferenceAPI preferenceAPI;
    public static LoginAPI loginAPI;
    public static AttractionAPI attractionAPI;
    public static ItineraryAPI itineraryAPI;
    public static TagsAPI tagsAPI;

    public static void init() {
        OkHttpClient client = new OkHttpClient();

        headerInterceptor = new HeaderInterceptor();
        client.interceptors().add(headerInterceptor);

        retrofitReactiveClient = new Retrofit.Builder()
                .baseUrl(SessionManager.getIPAddress())
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        userAPI = retrofitReactiveClient.create(UserAPI.class);
        userPreferenceAPI = retrofitReactiveClient.create(UserPreferenceAPI.class);
        preferenceAPI = retrofitReactiveClient.create(PreferenceAPI.class);
        loginAPI = retrofitReactiveClient.create(LoginAPI.class);
        attractionAPI = retrofitReactiveClient.create(AttractionAPI.class);
        itineraryAPI = retrofitReactiveClient.create(ItineraryAPI.class);
        tagsAPI = retrofitReactiveClient.create(TagsAPI.class);
    }
}

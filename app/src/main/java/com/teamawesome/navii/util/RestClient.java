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
import java.util.concurrent.TimeUnit;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by ecrothers on 07/31/16.
 */
public class RestClient {
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (NaviiPreferenceData.isLoggedIn()) {
                request = request.newBuilder()
                        .addHeader("X-AUTH-TOKEN", NaviiPreferenceData.getToken())
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
            return chain.proceed(request);
        }
    }

    public static UserAPI userAPI;
    public static UserPreferenceAPI userPreferenceAPI;
    public static PreferenceAPI preferenceAPI;
    public static LoginAPI loginAPI;
    public static AttractionAPI attractionAPI;
    public static ItineraryAPI itineraryAPI;
    public static TagsAPI tagsAPI;

    public static void init() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        NaviiPreferenceData.setIPAddress(Constants.SERVER_URL);

        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
        okHttpClient.interceptors().add(headerInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NaviiPreferenceData.getIPAddress())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        userAPI = retrofit.create(UserAPI.class);
        userPreferenceAPI = retrofit.create(UserPreferenceAPI.class);
        preferenceAPI = retrofit.create(PreferenceAPI.class);
        loginAPI = retrofit.create(LoginAPI.class);
        attractionAPI = retrofit.create(AttractionAPI.class);
        itineraryAPI = retrofit.create(ItineraryAPI.class);
        tagsAPI = retrofit.create(TagsAPI.class);
    }
}

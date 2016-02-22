package com.me.myroadtripmap.HTTP;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.me.myroadtripmap.TripInfo;
import com.me.myroadtripmap.TripInfoJsonDeserializer;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by developer3 on 1/3/16.
 */

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://api.automatic.com";
    //public static final String API_BASE_URL = "http://stagingquery.dabkick.com";
    public static final String TOKEN = "525b5d1d8f253877008012471d4f6955f3d12240";

    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
                    //.addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        httpClient.interceptors().clear();
        httpClient.interceptors().add(chain -> {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + TOKEN);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        gsonBuilder.registerTypeAdapter(TripInfo[].class, new TripInfoJsonDeserializer());
        builder.addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));

        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}

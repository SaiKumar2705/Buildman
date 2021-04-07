package com.buildman.buildman.web;

import android.content.Context;

import com.buildman.buildman.interfaces.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class NetworkClient {
    private static final String BASE_URL = "http://portal.buildmantech.com//webservice.asmx/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
             OkHttpClient httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
//            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

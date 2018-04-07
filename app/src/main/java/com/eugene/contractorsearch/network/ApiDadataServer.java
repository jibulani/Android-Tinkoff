package com.eugene.contractorsearch.network;


import com.eugene.contractorsearch.model.Contractor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDadataServer {

    private Api api;

    public ApiDadataServer() {
        //Логирование
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        //OkHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addNetworkInterceptor(httpLoggingInterceptor)
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();

        //Gson парсер
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Contractor.class, null);
//        gsonBuilder.registerTypeAdapter(Contractor.class, null);
        Gson gson = gsonBuilder.create();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://suggestions.dadata.ru/")
                .build();

        //Api server
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}

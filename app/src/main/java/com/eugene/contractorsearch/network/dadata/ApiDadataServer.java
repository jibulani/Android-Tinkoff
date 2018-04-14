package com.eugene.contractorsearch.network.dadata;


import com.eugene.contractorsearch.network.dadata.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDadataServer {

    private Api api;

    public ApiDadataServer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
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

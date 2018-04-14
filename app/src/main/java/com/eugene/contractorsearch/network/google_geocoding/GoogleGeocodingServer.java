package com.eugene.contractorsearch.network.google_geocoding;


import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.model.Coordinates;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleGeocodingServer {
    private static final String googleGeocodingBaseUrl = "https://maps.googleapis.com/";

    private Api api;

    public GoogleGeocodingServer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer());
        Gson gson = gsonBuilder.create();

        //Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(googleGeocodingBaseUrl)
                .build();

        //Api server
        api = retrofit.create(Api.class);
    }

    public Single<Coordinates> getCoordinates(String address, String key) {
        System.out.println(address.replaceAll(" ", "+"));
        return api.getCoordinates(address.replaceAll(" ", "+"), key);
    }
}

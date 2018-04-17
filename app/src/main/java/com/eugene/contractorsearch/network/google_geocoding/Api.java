package com.eugene.contractorsearch.network.google_geocoding;


import com.eugene.contractorsearch.db.Coordinates;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("maps/api/geocode/json")
    Single<Coordinates> getCoordinates(@Query("address") String address, @Query("key") String key);
}

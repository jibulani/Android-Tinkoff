package com.eugene.contractorsearch.network.dadata;


import com.eugene.contractorsearch.model.Suggestions;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers({"Authorization: Token 0700bd5c5f9131001447e3ef51930d62e77412c8", "Accept: application/json", "Content-Type: application/json"})
    @POST("suggestions/api/4_1/rs/suggest/party")
    Single<Suggestions> getContractors(@Body RequestObject requestObject);
}

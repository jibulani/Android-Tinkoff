package com.eugene.contractorsearch.network;


import com.eugene.contractorsearch.model.Suggestions;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    //POST с содержимым JSON
    @Headers({"Authorization: Token 0700bd5c5f9131001447e3ef51930d62e77412c8", "Accept: application/json", "Content-Type: application/json"})
    @POST("suggestions/api/4_1/rs/suggest/party")
    Single<Suggestions> getContractors(@Body RequestObject requestObject);
    /**
     * requestPost2(new TestObject(1,"testName",System.currentTimeMillis()))
     * ->
     * POST https://postman-echo.com/post
     * Content-Type: application/json; charset=UTF-8
     * {"id":1,"name":"testName","time":1522593510505}
     */
}

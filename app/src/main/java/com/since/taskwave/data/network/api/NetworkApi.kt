package com.since.taskwave.data.network.api

import com.since.taskwave.data.modal.network.QuoteNetwork
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {

    @GET("/quotes/random")
    suspend fun getQuotes() : Response<QuoteNetwork>

}
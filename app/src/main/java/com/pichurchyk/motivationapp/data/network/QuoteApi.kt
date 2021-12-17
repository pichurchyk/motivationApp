package com.pichurchyk.motivationapp.data.network

import com.pichurchyk.motivationapp.data.model.Quote
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    companion object {
        const val BASE_URL = "https://zenquotes.io/api/"
    }

    @GET("random")
    suspend fun getRandomQuote(
        @Query("mode") mode: String = "random",
    ): List<Quote>

    @GET("today")
    suspend fun getQuoteOfTheDay(
        @Query("mode") mode: String = "random",
    ): List<Quote>
}
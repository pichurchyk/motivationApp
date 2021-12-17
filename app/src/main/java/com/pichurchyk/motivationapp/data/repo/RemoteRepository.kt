package com.pichurchyk.motivationapp.data.repo

import com.pichurchyk.motivationapp.data.network.QuoteApi
import com.pichurchyk.motivationapp.ui.common.ResponseStatus
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val quoteApi: QuoteApi) {

    fun getRandomQuote() = flow {
        try {
            val response = quoteApi.getRandomQuote()
            emit(ResponseStatus.Success(response))
        } catch (e: Exception) {
            emit(ResponseStatus.Failure(e))
        }
    }

    fun getQuoteOfTheDay() = flow {
        try {
            val response = quoteApi.getQuoteOfTheDay()
            emit(ResponseStatus.Success(response))
        } catch (e: Exception) {
            emit(ResponseStatus.Failure(e))
        }
    }
}
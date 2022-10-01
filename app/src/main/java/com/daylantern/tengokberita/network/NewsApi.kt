package com.daylantern.tengokberita.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlinesByCountry(
        @Query("country") countryCode: String,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getTopHeadlinesByCategory(
        @Query("country") countryCode: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<NewsResponse>
}
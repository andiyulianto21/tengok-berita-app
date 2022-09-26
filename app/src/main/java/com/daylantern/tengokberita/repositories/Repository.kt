package com.daylantern.tengokberita.repositories

import com.daylantern.tengokberita.network.NewsApi
import com.daylantern.tengokberita.network.NewsResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getTopHeadlinesByCountry(countryCode: String) =
        newsApi.getTopHeadlinesByCountry(countryCode)
    suspend fun getTopHeadlinesByCategory(countryCode: String, category: String): Response<NewsResponse> =
        newsApi.getTopHeadlinesByCategory(countryCode, category)
}
package com.daylantern.tengokberita.repositories

import com.daylantern.tengokberita.network.NewsApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getTopHeadlinesByCountry(countryCode: String) = newsApi.getTopHeadlinesByCountry(countryCode)

}
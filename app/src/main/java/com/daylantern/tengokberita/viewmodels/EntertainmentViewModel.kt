package com.daylantern.tengokberita.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.daylantern.tengokberita.util.Constants
import com.daylantern.tengokberita.network.NewsApi
import com.daylantern.tengokberita.repositories.HealthPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntertainmentViewModel @Inject constructor(
    private val newsApi: NewsApi
): ViewModel() {

    val list = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE, Constants.PREFETCH_DISTANCE, enablePlaceholders = false)){
        HealthPagingSource(newsApi, "id", "entertainment")
    }.flow.cachedIn(viewModelScope)
}
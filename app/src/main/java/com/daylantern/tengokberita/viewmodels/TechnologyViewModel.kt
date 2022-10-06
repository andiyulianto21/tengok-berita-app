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
class TechnologyViewModel @Inject constructor(
    newsApi: NewsApi
): ViewModel() {

    val list = Pager(config = PagingConfig(Constants.PAGE_SIZE, Constants.PREFETCH_DISTANCE, false)){
        HealthPagingSource(newsApi, "id", "technology")
    }.flow.cachedIn(viewModelScope)

}
package com.daylantern.tengokberita.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.network.NewsApi
import com.daylantern.tengokberita.network.NewsResponse
import com.daylantern.tengokberita.repositories.HeadlinesPagingSource
import com.daylantern.tengokberita.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsApi: NewsApi
) : ViewModel() {

    val articlesList = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE, prefetchDistance = 2, enablePlaceholders = false)) {
        HeadlinesPagingSource(newsApi, "id")
    }.flow.cachedIn(viewModelScope)

}
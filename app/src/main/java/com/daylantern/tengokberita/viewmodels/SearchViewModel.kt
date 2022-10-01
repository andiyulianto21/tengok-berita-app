package com.daylantern.tengokberita.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.repositories.Repository
import com.daylantern.tengokberita.repositories.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    private var qry = ""

    fun setQuery(q: String) {
        qry = q
    }

    fun searchNews(): Flow<PagingData<Article>>? {
        if(qry.isNotEmpty()) {
            return repository.searchNews(qry).cachedIn(viewModelScope)
        }
        return null
    }


}


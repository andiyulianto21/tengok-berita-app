package com.daylantern.tengokberita.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _topHeadlines = MutableLiveData<List<Article>>()
    val topHeadlines: LiveData<List<Article>> get() = _topHeadlines

    fun getTopHeadlinesByCountry(countryCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTopHeadlinesByCountry(countryCode)
            if(response.isSuccessful){
                _topHeadlines.postValue(response.body()?.articles)
            }
        }
    }

}
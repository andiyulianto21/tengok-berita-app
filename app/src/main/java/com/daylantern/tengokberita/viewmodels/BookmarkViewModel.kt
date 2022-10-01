package com.daylantern.tengokberita.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var list: LiveData<List<SavedArticle>>

    init {
        getSavedArticle()
    }

    fun getList(): LiveData<List<SavedArticle>> {
        return list
    }

    private fun getSavedArticle(){
        list = repository.getSavedArticles()
    }

}
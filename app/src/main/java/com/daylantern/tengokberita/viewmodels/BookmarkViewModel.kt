package com.daylantern.tengokberita.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private var _list = MutableLiveData<List<SavedArticle>>()
    val list: LiveData<List<SavedArticle>> get() = _list

    init {
        getSavedArticle()
    }

    private fun getSavedArticle(){
        viewModelScope.launch(Dispatchers.IO) {
            _list.postValue(repository.getSavedArticles())
        }
    }

}
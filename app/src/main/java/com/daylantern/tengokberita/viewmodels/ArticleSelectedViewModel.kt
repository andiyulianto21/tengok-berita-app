package com.daylantern.tengokberita.viewmodels

import android.webkit.WebView
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
class ArticleSelectedViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private var _article = MutableLiveData<SavedArticle>()
    val article: LiveData<SavedArticle> get() = _article

    private var _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    fun setTitleToolbar(title: String) {
        _title.postValue(title)
    }

    private var _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> get() = _isSaved

    fun getArticle(url: String) = viewModelScope.launch(Dispatchers.IO) {
        _isSaved.postValue(repository.getArticle(url))
    }

    fun saveArticle(article: SavedArticle) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveArticle(article)
    }

    fun deleteArticle(url: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteArticle(url)
    }
}
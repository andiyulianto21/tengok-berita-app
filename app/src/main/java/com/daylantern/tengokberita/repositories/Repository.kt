package com.daylantern.tengokberita.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.database.SavedArticleDao
import com.daylantern.tengokberita.database.model.SavedArticle
import com.daylantern.tengokberita.network.NewsApi
import com.daylantern.tengokberita.network.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApi: NewsApi,
    private val savedArticleDao: SavedArticleDao
) {

    //network
    fun searchNews(query: String) = Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = 2,
                enablePlaceholders = false
            )
        ) {
            SearchPagingSource(newsApi, query)
        }.flow

        //database
        fun getSavedArticles(): LiveData<List<SavedArticle>> = savedArticleDao.getSavedArticles()
        fun getArticle(url: String): Boolean = savedArticleDao.getArticle(url)
        suspend fun saveArticle(article: SavedArticle) = savedArticleDao.saveArticle(article)
        suspend fun deleteArticle(url: String) = savedArticleDao.deleteArticle(url)
}
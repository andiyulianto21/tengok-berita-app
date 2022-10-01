package com.daylantern.tengokberita.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.network.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val query: String
): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageIndex = params.key ?: Constants.STARTING_PAGE_INDEX
            val response = newsApi.searchNews(query, pageIndex).body()
            val responseData = mutableListOf<Article>()
            val data = response?.articles ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if(pageIndex == Constants.STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if(responseData.isEmpty()) null else pageIndex + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
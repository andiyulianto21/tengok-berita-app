package com.daylantern.tengokberita.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daylantern.tengokberita.util.Constants
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.network.NewsApi
import retrofit2.HttpException
import java.io.IOException

class HealthPagingSource(
    private val newsApi: NewsApi,
    private val countryCode: String,
    private val category: String?= null
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
            val response = if(category != null) newsApi.getTopHeadlinesByCategory(countryCode, category, pageIndex)
                else newsApi.getTopHeadlinesByCountry(countryCode, pageIndex)
            val responseData = mutableListOf<Article>()
            val result = response.body()?.articles ?: emptyList()
            responseData.addAll(result)

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
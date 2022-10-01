package com.daylantern.tengokberita.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.daylantern.tengokberita.Constants
import com.daylantern.tengokberita.network.Article
import com.daylantern.tengokberita.network.NewsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HeadlinesPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val countryCode: String
): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val pageIndex = params.key ?: Constants.STARTING_PAGE_INDEX
        return try {
            val response = newsApi.getTopHeadlinesByCountry(countryCode, pageIndex)
            val responseData = mutableListOf<Article>()
            val data = response.body()?.articles ?: emptyList()
            responseData.addAll(data)
            val nextKey = if(responseData.isEmpty()) null else pageIndex + 1
            LoadResult.Page(
                data = responseData,
                prevKey = if(pageIndex == Constants.STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = nextKey
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
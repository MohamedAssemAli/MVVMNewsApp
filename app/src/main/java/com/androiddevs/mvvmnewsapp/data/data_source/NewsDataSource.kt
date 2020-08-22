package com.androiddevs.mvvmnewsapp.data.data_source

import android.util.Log
import androidx.paging.PagingSource
import com.androiddevs.mvvmnewsapp.data.api.ServiceBuilder
import com.androiddevs.mvvmnewsapp.data.models.Article
import com.androiddevs.mvvmnewsapp.data.models.NewsResponse
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Mohamed Assem on 22-Aug-2020.
 * mohamed.assem.ali@gmail.com
 * https://github.com/MohamedAssemAli
 */
class NewsDataSource(private val countryCode: String) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = ServiceBuilder.api.getPagedBreakingNews(countryCode, position)
            if (response.isSuccessful) {
                val articles = response.body()!!.articles
//                Log.d("Assem", "response.isSuccessful")
//                Log.d("Assem", "articles ${articles[0]}")
                LoadResult.Page(
                    data = articles,
//                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    prevKey = null, // Only paging forward.
                    nextKey = if (articles.isEmpty()) null else position + 1
//                    nextKey = if (articles.isEmpty()) null else (if (position < 2) (position + 1) else null)
                )
            } else {
                Log.d("Assem", "error")
                LoadResult.Error(Throwable(response.errorBody().toString()))
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
package com.androiddevs.mvvmnewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.androiddevs.mvvmnewsapp.data.api.ServiceBuilder
import com.androiddevs.mvvmnewsapp.data.data_source.NewsDataSource
import com.androiddevs.mvvmnewsapp.data.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.models.Article
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

class NewsRepository(val db: ArticleDatabase) {

    fun getPagedBreakingNews(countryCode: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsDataSource(countryCode) }
        ).flow
    }

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ServiceBuilder.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        ServiceBuilder.api.searchForNews(searchQuery, pageNumber)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}




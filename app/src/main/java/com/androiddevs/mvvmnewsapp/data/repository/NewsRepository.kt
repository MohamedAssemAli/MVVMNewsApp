package com.androiddevs.mvvmnewsapp.data.repository

import com.androiddevs.mvvmnewsapp.data.api.ServiceBuilder
import com.androiddevs.mvvmnewsapp.data.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.models.Article


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ServiceBuilder.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        ServiceBuilder.api.searchForNews(searchQuery, pageNumber)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}




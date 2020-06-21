package com.androiddevs.mvvmnewsapp.data.repository

import com.androiddevs.mvvmnewsapp.data.api.ServiceBuilder
import com.androiddevs.mvvmnewsapp.data.db.ArticleDatabase


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ServiceBuilder.api.getBreakingNews(countryCode, pageNumber)
}
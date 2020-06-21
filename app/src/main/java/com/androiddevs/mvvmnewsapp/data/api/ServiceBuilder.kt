package com.androiddevs.mvvmnewsapp.data.api

import com.androiddevs.mvvmnewsapp.util.Constants.Companion.API_KEY
import com.androiddevs.mvvmnewsapp.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Mohamed Assem on 21-Jun-20.
 * mo7mad.assim@gmail.com
 */

class ServiceBuilder {

    companion object {
        private val retrofit by lazy {
            // Create a Custom Interceptor to add API_KEY to each request
            val requestInterceptor = Interceptor { chain ->
                val url =
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build()

                val request =
                    chain.request().newBuilder().url(url)
                        .build()
                return@Interceptor chain.proceed(request)
            }

            // Create OkHttp Client
            val okHttp = OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()


        }

        val api by lazy {
            retrofit.create(
                NewsAPI::class.java
            )
        }

    }
}
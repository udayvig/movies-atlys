package com.example.movies.common

import com.example.movies.BuildConfig
import okhttp3.OkHttpClient

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val AUTHORIZATION = "Authorization"
    private const val BEARER = "Bearer "

    fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addNetworkInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(AUTHORIZATION, BEARER + BuildConfig.API_KEY)
            chain.proceed(requestBuilder.build())
        }

        return httpClient.build()
    }
}
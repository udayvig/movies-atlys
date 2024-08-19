package com.example.movies.common

import com.example.movies.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val FETCH_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    private const val AUTHORIZATION = "Authorization"
    private const val BEARER = "Bearer "

    const val SERVER_EXCEPTION_MESSAGE: String = "Something went wrong :("
    const val IO_EXCEPTION_MESSAGE: String = "Couldn't reach server, please check your internet " +
            "connection and try again."

    fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addNetworkInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.addHeader(AUTHORIZATION, BEARER + BuildConfig.API_KEY)
            chain.proceed(requestBuilder.build())
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        httpClient.addInterceptor(interceptor = interceptor)

        return httpClient.build()
    }
}
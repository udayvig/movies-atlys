package com.example.movies.data.remote

import com.example.movies.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): MovieDto

    @GET("search/movie/")
    suspend fun getQueryMovies(@Query("query") query: String): MovieDto
}
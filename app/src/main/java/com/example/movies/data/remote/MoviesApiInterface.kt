package com.example.movies.data.remote

import com.example.movies.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    @GET("/trending/movie")
    suspend fun getTrendingMovies(): List<MovieDto>

    @GET("/search/movie")
    suspend fun getQueryMovie(@Query("query") query: String): List<MovieDto>
}
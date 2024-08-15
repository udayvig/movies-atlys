package com.example.movies.domain.repository

import com.example.movies.data.remote.dto.MovieDto

interface MovieRepository {
    suspend fun getTrendingMovies(): MovieDto

    suspend fun getQueryMovies(query: String): MovieDto
}
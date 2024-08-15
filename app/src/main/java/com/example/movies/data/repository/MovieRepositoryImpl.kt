package com.example.movies.data.repository

import com.example.movies.data.remote.MoviesApiInterface
import com.example.movies.data.remote.dto.MovieDto
import com.example.movies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApiInterface
): MovieRepository {

    override suspend fun getTrendingMovies(): MovieDto {
        return moviesApi.getTrendingMovies()
    }

    override suspend fun getQueryMovies(query: String): MovieDto {
        return moviesApi.getQueryMovies(query = query)
    }
}
package com.example.movies.domain.usecases.gettrendingmovies

import com.example.movies.common.Constants
import com.example.movies.common.Resource
import com.example.movies.data.remote.dto.toMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getTrendingMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val trendingMovies = movieRepository.getTrendingMovies().results.map { it.toMovie() }
            emit(Resource.Success(trendingMovies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: Constants.SERVER_EXCEPTION_MESSAGE))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: Constants.IO_EXCEPTION_MESSAGE))
        }
    }
}
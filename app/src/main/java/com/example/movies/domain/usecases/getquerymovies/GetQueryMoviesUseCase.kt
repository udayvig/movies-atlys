package com.example.movies.domain.usecases.getquerymovies

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

class GetQueryMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getQueryMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val queryMovies = movieRepository.getQueryMovies(query = query).results.map { it.toMovie() }
            emit(Resource.Success(queryMovies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: Constants.SERVER_EXCEPTION_MESSAGE))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: Constants.IO_EXCEPTION_MESSAGE))
        }
    }
}
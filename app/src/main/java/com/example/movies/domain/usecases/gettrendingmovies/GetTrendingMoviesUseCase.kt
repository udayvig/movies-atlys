package com.example.movies.domain.usecases.gettrendingmovies

import com.example.movies.common.Resource
import com.example.movies.data.remote.dto.MovieDto
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

    suspend fun getQueryMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val queryMovies = movieRepository.getQueryMovies(query = query).results.map { it.toMovie() }
            emit(Resource.Success(queryMovies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: SERVER_EXCEPTION_MESSAGE))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: IO_EXCEPTION_MESSAGE))
        }
    }

    companion object {
        private const val SERVER_EXCEPTION_MESSAGE: String = "Something went wrong :("
        private const val IO_EXCEPTION_MESSAGE: String = "Couldn't reach server, please check your internet " +
                "connection and try again."
    }
}
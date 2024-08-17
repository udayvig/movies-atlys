package com.example.movies.presentation.movieslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.Resource
import com.example.movies.domain.usecases.gettrendingmovies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MoviesListState())
    val state: State<MoviesListState> = _state

    init {
        getMovies()
    }

    private fun getMovies() {
        getTrendingMoviesUseCase.getTrendingMovies().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MoviesListState(movies = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = MoviesListState(error = result.message?: "An unexpected error occurred.")
                }

                is Resource.Loading -> {
                    _state.value = MoviesListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
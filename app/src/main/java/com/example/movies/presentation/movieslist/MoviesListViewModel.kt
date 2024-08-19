package com.example.movies.presentation.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.Resource
import com.example.movies.domain.usecases.getquerymovies.GetQueryMoviesUseCase
import com.example.movies.domain.usecases.gettrendingmovies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getQueryMoviesUseCase: GetQueryMoviesUseCase
) : ViewModel() {

    private val _queryString = MutableStateFlow("")
    val queryString = _queryString.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _state = MutableStateFlow(MoviesListState())
    val state = _state.asStateFlow()

    init {
        queryString
            .debounce(DEBOUNCE_TIMEOUT)
            .onEach { query ->
                if (query.isBlank()) {
                    getMovies()
                } else {
                    searchMovies(query)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _queryString.value = query
    }

    private fun getMovies() {
        getTrendingMoviesUseCase.getTrendingMovies().onEach { result ->
            _isSearching.update {
                result is Resource.Loading
            }

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            movies = result.data ?: emptyList(),
                            isLoading = false,
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "${STATE_ERROR_MESSAGE}\n${result.message}"
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchMovies(query: String) {
        getQueryMoviesUseCase.getQueryMovies(query).onEach { result ->
            _isSearching.update {
                result is Resource.Loading
            }

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            movies = result.data ?: emptyList(),
                            isLoading = false,
                            error = ""
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "${STATE_ERROR_MESSAGE}\n${result.message}"
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        private const val DEBOUNCE_TIMEOUT: Long = 500L
        private const val STATE_ERROR_MESSAGE = "An unexpected error occurred."
    }
}
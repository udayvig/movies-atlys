package com.example.movies.presentation.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.Resource
import com.example.movies.domain.usecases.getquerymovies.GetQueryMoviesUseCase
import com.example.movies.domain.usecases.gettrendingmovies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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

    @OptIn(FlowPreview::class)
    val state = queryString
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_state) { queryString, state ->
            if (queryString.isBlank()) {
                if (state.error.isBlank()) {
                    getMovies()
                }

                state
            } else {
                searchMovies(queryString)
                state
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onSearchQueryChange(query: String) {
        _queryString.value = query
    }

    private fun getMovies() {
        getTrendingMoviesUseCase.getTrendingMovies().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred."
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun searchMovies(query: String) {
        getQueryMoviesUseCase.getQueryMovies(query = query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _isSearching.value = false
                    _state.value = _state.value.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    _isSearching.value = false
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred."
                    )
                }

                is Resource.Loading -> {
                    _isSearching.value = true
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
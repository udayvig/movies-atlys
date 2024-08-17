package com.example.movies.presentation.ui.theme

import com.example.movies.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data object MoviesListRoute

@Serializable
data class MovieDetailsRoute (
    val movieDetails: Movie
)
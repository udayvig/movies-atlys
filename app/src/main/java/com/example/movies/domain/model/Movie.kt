package com.example.movies.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val backdropPath: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val title: String
)

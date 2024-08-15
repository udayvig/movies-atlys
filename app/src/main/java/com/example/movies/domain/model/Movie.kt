package com.example.movies.domain.model

data class Movie(
    val id: Int,
    val backdropPath: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val title: String
)

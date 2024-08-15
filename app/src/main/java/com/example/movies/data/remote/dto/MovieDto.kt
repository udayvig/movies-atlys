package com.example.movies.data.remote.dto

import com.example.movies.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    val page: Int,
    val results: List<MovieObjectDto>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieObjectDto(
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    val id: Int,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val title: String,
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
)

fun MovieObjectDto.toMovie(): Movie {
    return Movie(
        id = id,
        backdropPath = backdropPath,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        title = title
    )
}
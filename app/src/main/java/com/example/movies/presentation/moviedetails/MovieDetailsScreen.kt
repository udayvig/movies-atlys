package com.example.movies.presentation.moviedetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.movies.domain.model.Movie

@Composable
fun MovieDetailsScreen(
    movie: Movie
) {
    Text(text = movie.toString())
}
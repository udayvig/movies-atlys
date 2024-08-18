package com.example.movies.presentation.movieslist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movie) }
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(15.dp)),
            error = painterResource(id = R.drawable.baseline_error_outline_24)
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}
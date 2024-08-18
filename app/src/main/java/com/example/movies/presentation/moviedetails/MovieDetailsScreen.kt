package com.example.movies.presentation.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.model.Movie

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movie: Movie
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            onClick = { navController.popBackStack() },
            content = {
                Image(painter = painterResource(id = R.drawable.baseline_chevron_left_24), contentDescription = null)
            },
            modifier = Modifier
                .padding(top = 10.dp)
        )

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + movie.posterPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 40.dp, end = 40.dp)
                .clip(RoundedCornerShape(15.dp)),
            error = painterResource(id = R.drawable.baseline_error_outline_24)
        )

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 30.dp, top = 30.dp, start = 20.dp)
        )

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp)
        )
    }
}
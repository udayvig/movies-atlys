package com.example.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.moviedetails.MovieDetailsScreen
import com.example.movies.presentation.movieslist.MoviesListScreen
import com.example.movies.presentation.ui.CustomNavType
import com.example.movies.presentation.ui.theme.MovieDetailsRoute
import com.example.movies.presentation.ui.theme.MoviesListRoute
import com.example.movies.presentation.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                Surface(color = Color.White) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = MoviesListRoute
                    ) {
                        composable<MoviesListRoute> {
                            MoviesListScreen(
                                onMovieClick = { movie ->
                                    navController.navigate(
                                        MovieDetailsRoute(movieDetails = movie)
                                    )
                                }
                            )
                        }

                        composable<MovieDetailsRoute> (
                            typeMap = mapOf(
                                typeOf<Movie>() to CustomNavType.MovieType
                            )
                        ) {
                            val arguments = it.toRoute<MovieDetailsRoute>()
                            MovieDetailsScreen(
                                movie = arguments.movieDetails
                            )
                        }
                    }
                }
            }
        }
    }
}
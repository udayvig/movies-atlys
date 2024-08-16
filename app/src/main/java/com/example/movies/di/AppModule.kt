package com.example.movies.di

import com.example.movies.common.Constants
import com.example.movies.data.remote.MoviesApiInterface
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MoviesApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Constants.getOkHttpClient())
            .build()
            .create(MoviesApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(moviesApiInterface: MoviesApiInterface): MovieRepository {
        return MovieRepositoryImpl(moviesApiInterface)
    }
}
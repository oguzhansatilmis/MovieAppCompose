package com.example.movieappcompose.repository

import com.example.movieappcompose.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository() {

    suspend fun getMovie(page: Int) = safeApiCall {
        apiService.getMovies(page)
    }

    suspend fun getMoviesById(movieId: Long) = safeApiCall {
        apiService.getMovieById(movieId)
    }

}
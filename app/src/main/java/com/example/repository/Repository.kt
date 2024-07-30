package com.example.repository

import com.example.movieappcompose.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository() {

    suspend fun getMovie(page:Int) = safeApiCall {
        apiService.getMovies(page)
    }
}
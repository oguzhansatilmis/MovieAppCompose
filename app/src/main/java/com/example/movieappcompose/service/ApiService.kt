package com.example.movieappcompose.service

import com.example.movieappcompose.model.DisplayResponse
import com.example.movieappcompose.model.Movie
import com.example.movieappcompose.model.MovieResponse
import com.example.movieappcompose.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ) : Response<MovieResponse>



    @GET("/3/movie/{id}")
    suspend fun getMovieById(
        @Path("id") id : Long,
        @Query("api_key") apiKey : String = Constants.API_KEY,
    ) : Response<Movie>


    @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey : String = Constants.API_KEY
    ) : Response<DisplayResponse>


}
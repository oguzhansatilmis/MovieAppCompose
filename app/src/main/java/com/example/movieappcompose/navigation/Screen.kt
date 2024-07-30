package com.example.movieappcompose.navigation


sealed class Screen(val route :String){

    object Movie:Screen(route = "movie_screen")
    object MovieDetail:Screen(route = "movieDetail_screen")
}
package com.example.movieappcompose.navigation


sealed class Screen(val route :String){

    data object Movie:Screen(route = "movie_screen")
    data object MovieDetail:Screen(route = "movieDetail_screen")
}
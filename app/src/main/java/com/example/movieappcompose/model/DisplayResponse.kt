package com.example.movieappcompose.model

data class DisplayResponse(
    var page : Int,
    var results : MutableList<Movie>,
)

package com.example.movieappcompose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun DetailScreen(navController: NavController,movieId:String?) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Detail Screen $movieId")
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    DetailScreen(navController,"12")
}
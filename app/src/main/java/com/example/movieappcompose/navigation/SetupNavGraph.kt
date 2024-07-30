package com.example.movieappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieappcompose.detail.DetailScreen
import com.example.movieappcompose.movie.MovieScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {
    
    NavHost(navController = navController, startDestination = Screen.Movie.route) {
        composable(route = Screen.Movie.route) {
            MovieScreen(navController = navController)
        }
        composable(route = Screen.MovieDetail.route+"?movieId={movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                   defaultValue = "default value"
                }
            )
        ) {

            val movieId = it.arguments?.getString("movieId")

            DetailScreen(navController,movieId)
        }
    }
}
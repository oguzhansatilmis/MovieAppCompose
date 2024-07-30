package com.example.movieappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieappcompose.navigation.SetupNavGraph
import com.example.movieappcompose.ui.theme.MovieAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var navController: NavHostController
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            SetupNavGraph(navController = navController)
//            MovieAppComposeTheme {
//                DetailScreen()
//            }
        }
    }
}
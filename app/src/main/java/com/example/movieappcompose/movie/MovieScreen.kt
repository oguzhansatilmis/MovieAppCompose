package com.example.movieappcompose.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappcompose.R
import com.example.movieappcompose.model.Movie
import com.example.movieappcompose.model.Resource
import com.example.movieappcompose.navigation.Screen


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(navController: NavController) {

    val viewModel: MovieViewModel = hiltViewModel()
    viewModel.getPopularMovies(1)
    val state by viewModel.popularMovies.observeAsState()

    var searchMovieQuery by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = "Contents")

                        Image(
                            painter = painterResource(id = R.drawable.gridicon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .align(alignment = Alignment.CenterEnd),
                            alignment = Alignment.CenterEnd
                        )
                    }

                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier.fillMaxWidth(0.9f)) {

                    TextField(
                        value = searchMovieQuery,
                        onValueChange = { searchMovieQuery = it },
                        placeholder = { Text(text = "Search") },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "",
                        alignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .size(20.dp, 20.dp)
                            .align(alignment = Alignment.CenterEnd)
                    )

                }

            }
            when (state) {
                is Resource.Success -> {
                    val data = (state as Resource.Success).data
                    data?.let {
                        MovieLazyColumn(popularMovies = data.results,navController)
                    }
                }

                is Resource.Loading -> {}
                is Resource.Error -> {}
                else -> {
                    Text(text = "Error")
                }
            }

        }

    }

}

@Composable
fun MovieLazyColumn(popularMovies: List<Movie>,navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(popularMovies.size) { index ->
            val movie = popularMovies[index]
            MovieItemView(movie){
                navController.navigate("${Screen.MovieDetail.route}?movieId=$it")
            }
        }
    }
}

//@Composable
//fun MovieVerticalGrid(popularMovies: List<Movie>) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        contentPadding = PaddingValues(8.dp)
//    ) {
//        items(popularMovies.size) { index ->
//            val movie = popularMovies[index]
//            MovieItemView(movie)
//        }
//    }
//}


@Preview
@Composable
fun Preview() {
    val navController = rememberNavController()
    MovieScreen(navController)
}
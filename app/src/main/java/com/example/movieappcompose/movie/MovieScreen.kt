package com.example.movieappcompose.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappcompose.R
import com.example.movieappcompose.model.Movie
import com.example.movieappcompose.model.MovieResponse
import com.example.movieappcompose.model.Resource
import com.example.movieappcompose.navigation.Screen
import com.example.movieappcompose.util.TotalMovieList.totalMovieList


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(navController: NavController) {

    val viewModel: MovieViewModel = hiltViewModel()
    viewModel.getPopularMovies(1)
    val state by viewModel.popularMovies.observeAsState()
    var iconState by remember { mutableStateOf(false) }

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
                            painter = painterResource(id = if (iconState) R.drawable.gridicon else R.drawable.listicon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .align(alignment = Alignment.CenterEnd)
                                .clickable {
                                    iconState = !iconState
                                },
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
            when (state) {
                is Resource.Success -> {
                    val data = (state as Resource.Success).data
                    data?.let {
                        addPageIfNotExists(data)

                        if (iconState){
                            MovieLazyColumnGrid(
                                popularMovies = totalMovieList.flatMap { it.results },
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        else{
                            MovieLazyColumnList(
                                popularMovies = totalMovieList.flatMap { it.results },
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                    }
                }

                is Resource.Loading -> {}
                is Resource.Error -> {}
                else -> {}
            }
        }
    }
}

@Composable
fun MovieLazyColumnGrid(
    popularMovies: List<Movie>,
    navController: NavController,
    viewModel: MovieViewModel
) {
    var pageCount by remember { mutableIntStateOf(1) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(popularMovies.size) { index ->
            val movie = popularMovies[index]
            MovieItemViewGrid(movie) {
                navController.navigate("${Screen.MovieDetail.route}?movieId=$it")
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        pageCount++
                        viewModel.getPopularMovies(pageCount)
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                ) {
                    Text(text = "Load More")
                }
            }
        }
    }
}

@Composable
fun MovieLazyColumnList(
    popularMovies: List<Movie>,
    navController: NavController,
    viewModel: MovieViewModel
) {
    var pageCount by remember { mutableIntStateOf(1) }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        items(popularMovies.size) { index ->
            val movie = popularMovies[index]
            MovieItemViewList(movie) {
                navController.navigate("${Screen.MovieDetail.route}?movieId=$it")
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = {
                        pageCount++
                        viewModel.getPopularMovies(pageCount)
                    },
                    modifier = Modifier
                ) {
                    Text(text = "Load More")
                }
            }
        }
    }
}

private fun addPageIfNotExists(newPage: MovieResponse) {
    val pageExists = totalMovieList.any { it.page == newPage.page }
    if (!pageExists) {
        totalMovieList.add(newPage)
    }
}

@Preview
@Composable
fun Preview() {
    val navController = rememberNavController()
    MovieScreen(navController)
}
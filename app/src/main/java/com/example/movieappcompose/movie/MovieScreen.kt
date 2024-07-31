package com.example.movieappcompose.movie

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.util.query
import com.example.movieappcompose.R
import com.example.movieappcompose.model.DisplayResponse
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
    val searchState by viewModel.searchMovies.collectAsState()
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
                                .align(alignment = Alignment.CenterEnd)
                                .clickable {


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
                        onValueChange = { query ->
                            searchMovieQuery = query
                            viewModel.searchMovie(searchMovieQuery)
                        },
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
                        addPageIfNotExists(it)
                        MovieLazyColumn(
                            popularMovies = totalMovieList.flatMap { it.results },
                            navController = navController,
                            viewModel = viewModel
                        )
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
fun MovieLazyColumn(
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
            MovieItemView(movie) {
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
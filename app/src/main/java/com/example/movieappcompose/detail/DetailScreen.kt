package com.example.movieappcompose.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieappcompose.R
import com.example.movieappcompose.model.Resource
import com.example.movieappcompose.movie.MovieLazyColumn


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, movieId: String) {

    val viewModel: DetailViewModel = hiltViewModel()

    viewModel.getPopularMovieById(movieId.toLong())

    val detailState by viewModel.popularMoviesById.observeAsState()

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
                        Text(text = "Contents Detail")

                        Image(
                            painter = painterResource(id = R.drawable.star_2),
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
        when (detailState) {

            is Resource.Success -> {
                val data = (detailState as Resource.Success).data

                data?.let {
                    Column(modifier = Modifier.fillMaxSize()) {

                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w200" + data.posterPath,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(8.dp)
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
                        ) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)) {
                                Text(text = data.title,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 24.sp
                                )
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Text(text = data.overview, modifier = Modifier.fillMaxWidth())
                                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                                Text(text = "Vote Count:${data.voteCount}", modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }

            }

            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Error -> {}
            else -> {}
        }

    }
}


@Preview
@Composable
fun DetailScreenPreview() {
  val navController = rememberNavController()
    DetailScreen(navController, "12")

}
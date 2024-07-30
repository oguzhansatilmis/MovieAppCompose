package com.example.movieappcompose.movie

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappcompose.R
import com.example.movieappcompose.model.Movie

@Composable
fun MovieItemView(movie: Movie,onClick: (movieId:String) -> Unit) {
    Column(modifier = Modifier
        .height(256.dp)
        .clickable { onClick(movie.id.toString()) }) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w200"+movie.backdropPath,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .background(colorResource(id = R.color.banner_black))
                .fillMaxWidth(),
            //    .align(Alignment.BottomCenter),
            textAlign = TextAlign.Center,
            color = Color.White
        )
   }
}



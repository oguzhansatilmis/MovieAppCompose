package com.example.movieappcompose.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.model.FavoriteMovie
import com.example.movieappcompose.model.Movie
import com.example.movieappcompose.model.Resource
import com.example.movieappcompose.repository.Repository
import com.example.movieappcompose.util.TotalMovieList.totalMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    private val _popularMoviesById = MutableLiveData<Resource<Movie>>(Resource.Loading())
    val popularMoviesById : LiveData<Resource<Movie>> = _popularMoviesById

    fun getPopularMovieById(movieId:Long){
        viewModelScope.launch {
            val movie = repository.getMoviesById(movieId)
            movie?.let { response->
                if (response.isSuccessful){
                    val movieBody = response.body()
                    movieBody?.let { movies->
                        _popularMoviesById.value = Resource.Success(movies)
                    }
                }
                else{
                    _popularMoviesById.value = Resource.Error("Network Error")
                }
            }
        }
    }
}
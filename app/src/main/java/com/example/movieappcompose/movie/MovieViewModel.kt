package com.example.movieappcompose.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.model.DisplayResponse
import com.example.movieappcompose.model.MovieResponse
import com.example.movieappcompose.model.Resource
import com.example.movieappcompose.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Encapsulation
    private val _popularMovies = MutableLiveData<Resource<MovieResponse>>(Resource.Loading())
    val popularMovies: LiveData<Resource<MovieResponse>> = _popularMovies

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            val popularMovies = repository.getMovie(page)
            popularMovies?.let {

                if (popularMovies.isSuccessful) {

                    val result = popularMovies.body()

                    result?.let { response ->
                        _popularMovies.value = Resource.Success(response)
                    }
                } else {
                    _popularMovies.value = Resource.Error(message = "Network Error")
                }
            }
        }
    }
}
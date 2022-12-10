package com.example.wovie.ui.film

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.Genre
import com.example.wovie.ui.model.Actor
import com.example.wovie.ui.model.Film
import com.example.wovie.util.toActor
import com.example.wovie.util.toFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    val genres = MutableLiveData<List<Genre>?>()
    val msg = MutableLiveData<String>()

    val actors = MutableLiveData<List<Actor>>()

    val recommended = MutableLiveData<List<Film>>()

    fun getGenresList() {
        viewModelScope.launch {
            try {
                val res = apiService.getGenres()
                genres.postValue(res?.genres)
            } catch (exception: Exception) {
                msg.postValue("Something went wrong")
            }
        }
    }

    fun getCast(movieId: Int) {
        viewModelScope.launch {
            try {
                val castResponse = apiService.getActorsByMovie(movieId)
                castResponse?.let {
                    actors.postValue(it.cast.map { response ->
                        response.toActor()
                    })
                }
            } catch (exception: Exception) {
                val e = exception
                msg.postValue("Something went wrong")
            }
        }
    }

    fun getRecommendedList(movieId: Int) {
        viewModelScope.launch {
            try {
                val res = apiService.getRecommendedByMovie(movieId)
                val list = mutableListOf<Film>()
                res?.results?.forEach { response ->
                    list.add(response.toFilm())
                }
                recommended.postValue(list)
            } catch (exception: Exception) {
                val e = exception
                msg.postValue("Something went wrong")
            }
        }
    }
}
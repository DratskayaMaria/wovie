package com.example.wovie.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.NowPlaying
import com.example.wovie.api.response.Popular
import com.example.wovie.api.response.TopRated
import com.example.wovie.api.response.Upcoming
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.ui.model.Film
import com.example.wovie.util.IdlingResource
import com.example.wovie.util.toFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    val popularMutableLiveData = MutableLiveData<List<Film>>()
    val nowPlayingMutableLiveData = MutableLiveData<List<Film>>()
    val topRatedMutableLiveData = MutableLiveData<List<Film>>()
    val upcomingMutableLiveData = MutableLiveData<List<Film>>()

    fun getFilms() {
        IdlingResource.countingIdlingResource.increment()
        viewModelScope.launch {
            try {
                coroutineScope {
                    delay(1000)
                    loading.postValue(true)
                    try {
                        var popularResults: Popular? = null
                        var nowPlayingResults: NowPlaying? = null
                        var topRatedResults: TopRated? = null
                        var upcomingResults: Upcoming? = null
                        var bookmarkedMovies: List<Int> = mutableListOf()
                        val popular = async {
                            popularResults = apiService.getPopularMovies()
                            nowPlayingResults = apiService.getNowPlayingMovies()
                            topRatedResults = apiService.getTopRatedMovies()
                            upcomingResults = apiService.getUpcomingMovies()
                            bookmarkedMovies = bookmarkRepository.getAllBookmarks().map { it.movieId }
                        }
                        popular.await().let {
                            Log.i("awaited",it.toString())
                            popularMutableLiveData.postValue(popularResults?.results?.map { response ->
                                response.toFilm(bookmarkedMovies.contains(response.id))
                            })
                            nowPlayingMutableLiveData.postValue(nowPlayingResults?.results?.map { response ->
                                response.toFilm(bookmarkedMovies.contains(response.id))
                            })
                            topRatedMutableLiveData.postValue(topRatedResults?.results?.map { response ->
                                response.toFilm(bookmarkedMovies.contains(response.id))
                            })
                            upcomingMutableLiveData.postValue(upcomingResults?.results?.map { response ->
                                response.toFilm(bookmarkedMovies.contains(response.id))
                            })
                            IdlingResource.countingIdlingResource.decrement()
                        }
                    } catch (exception: Exception) {
                        Log.i("popular exeception", exception.message.toString())
                    }

                    loading.postValue(false)
                }
            }
            catch (unknownHostException:UnknownHostException) {
                Log.i("error",unknownHostException.message.toString())
                msg.postValue("No internet connection")
            }
            catch (exception :Exception){
                Log.i("error",exception.message.toString())
            }

        }

    }

    fun setBookMarkStatus(film: Film) {
        viewModelScope.launch {
            try {
                if (!film.isBookmarked) {
                    bookmarkRepository.insertBookmarkedMovie(film.filmId)
                } else {
                    bookmarkRepository.deleteBookmarkedMovie(film.filmId)
                }
            } catch (e: Exception) {
                Log.i("bookmark", e.message.toString())
                msg.postValue("operation failed")
            }
        }
    }
}
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
import com.example.wovie.util.toFilm
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    init {
        getFilms()
        loading.postValue(false)
    }

    fun getFilms() {
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
                        val popular = async {
                            popularResults = apiService.getPopularMovies()
                            nowPlayingResults = apiService.getNowPlayingMovies()
                            topRatedResults = apiService.getTopRatedMovies()
                            upcomingResults = apiService.getUpcomingMovies()
                        }
                        popular.await().let {
                            Log.i("awaited",it.toString())
                            popularMutableLiveData.postValue(popularResults?.results?.map { response ->
                                response.toFilm()
                            })
                            nowPlayingMutableLiveData.postValue(nowPlayingResults?.results?.map { response ->
                                response.toFilm()
                            })
                            topRatedMutableLiveData.postValue(topRatedResults?.results?.map { response ->
                                response.toFilm()
                            })
                            upcomingMutableLiveData.postValue(upcomingResults?.results?.map { response ->
                                response.toFilm()
                            })
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
        film.isBookmarked = !film.isBookmarked
        loading.postValue(true)
        viewModelScope.launch {
            try {
                if (film.isBookmarked) {
                    bookmarkRepository.insertBookmarkedMovie(film.filmId)
                } else {
                    bookmarkRepository.deleteBookmarkedMovie(film.filmId)
                }
            } catch (e: Exception) {
                msg.postValue("operation failed")
            } finally {
                loading.postValue(false)
            }
        }
    }
}

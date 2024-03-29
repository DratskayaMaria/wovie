package com.example.wovie.ui.bookmarks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.ui.model.Film
import com.example.wovie.util.IdlingResource
import com.example.wovie.util.toFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val apiService: ApiService
) : ViewModel() {
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val bookMarkResults = MutableLiveData<List<Film>>()

    fun setBookMarkStatus(film: Film) {
        IdlingResource.increment()
        viewModelScope.launch {
            try {
                if (!film.isBookmarked) {
                    bookmarkRepository.insertBookmarkedMovie(film.filmId)
                } else {
                    bookmarkRepository.deleteBookmarkedMovie(film.filmId)
                }
            } catch (e: Exception) {
                Log.e("set bookmark", "setBookMarkStatus ${film.isBookmarked}: ${e.message}", )
                msg.postValue("operation failed")
            }
            IdlingResource.decrement()
        }
    }

    fun clearBookMarks() {
        IdlingResource.increment()
        viewModelScope.launch {
            try {
                coroutineScope {
                    try {
                        bookmarkRepository.deleteAllBookmarks()
                        getData()
                    } catch (e: Exception) {
                        msg.postValue("something went wrong")
                    }
                }
            } catch (e: Exception) {
                Log.e("clear bookmarks error", e.message.toString())
                msg.postValue("something went wrong")
            }
            IdlingResource.decrement()
        }
    }

    public fun getData() {
        IdlingResource.increment()
        viewModelScope.launch {
            delay(1000)
            loading.postValue(true)
            try {
                val results = bookmarkRepository.getAllBookmarks()
                    .map { bookmark ->
                        apiService.getMovieById(bookmark.movieId).toFilm(true)
                    }
                bookMarkResults.postValue(results)
            } catch (e: Exception) {
                Log.i("get data error bookmark", e.message.toString())
            }
            loading.postValue(false)
            IdlingResource.decrement()
        }
    }
}
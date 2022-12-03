package com.example.wovie.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.ui.model.Film
import javax.inject.Inject
import kotlinx.coroutines.launch

class MainViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

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

package com.example.wovie.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.FilmResponse
import com.example.wovie.api.response.SearchResponse
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.ui.model.Film
import com.example.wovie.util.IdlingResource
import com.example.wovie.util.toFilm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiService: ApiService,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    val searchResultMutableLiveData = MutableLiveData<List<Film>>()
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    init {
        loading.value = false
    }

    fun getSearchResults(query: String, hasConnection: Boolean) {
        if (hasConnection) {
            IdlingResource.increment()
            viewModelScope.launch {
                loading.postValue(true)
                try {
                    var searchResults: SearchResponse? = null
                    var bookmarkedMovies: List<Int> = mutableListOf()
                    val asyncRes = async {
                        searchResults = apiService.getSearchResults(query)
                        bookmarkedMovies = bookmarkRepository.getAllBookmarks().map { it.movieId }
                    }
                    asyncRes.await().let {
                        searchResultMutableLiveData.postValue(searchResults?.results?.map { response ->
                            response.toFilm(bookmarkedMovies.contains(response.id))
                        })
                    }

                } catch (exception: Exception) {
                    searchResultMutableLiveData.postValue(mutableListOf())
                    msg.postValue("Something went wrong")
                } finally {
                    loading.postValue(false)
                    IdlingResource.decrement()
                }
            }
        } else {
            msg.postValue("No internet connection")
            IdlingResource.decrement()
        }
    }
}

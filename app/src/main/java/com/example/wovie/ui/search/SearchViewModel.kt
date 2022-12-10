package com.example.wovie.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    val searchResultMutableLiveData = MutableLiveData<SearchResponse?>()
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    init {
        loading.value = false
    }

    fun getSearchResults(query: String, hasConnection: Boolean) {
        if (hasConnection) {
            viewModelScope.launch {
                loading.postValue(true)
                try {
                    val res = apiService.getSearchResults(query)
                    searchResultMutableLiveData.postValue(res)
                } catch (exception: Exception) {
                    searchResultMutableLiveData.postValue(SearchResponse())
                    msg.postValue("Something went wrong")
                } finally {
                    loading.postValue(false)
                }
            }
        } else {
            msg.postValue("No internet connection")
        }
    }
}
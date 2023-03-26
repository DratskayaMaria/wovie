package com.example.wovie.ui.actor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wovie.api.ApiService
import com.example.wovie.ui.model.Actor
import com.example.wovie.util.IdlingResource
import com.example.wovie.util.toActor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class ActorViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val actor = MutableLiveData<Actor>()
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun getActorInfo(personId: Int) {
        IdlingResource.increment()
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val resp = apiService.getActorById(personId)
                resp?.let {
                    actor.postValue(it.toActor())
                }
            } catch (exception: Exception) {
                val e = exception
                msg.postValue("Something went wrong")
            }
            loading.postValue(false)
            IdlingResource.decrement()
        }
    }
}
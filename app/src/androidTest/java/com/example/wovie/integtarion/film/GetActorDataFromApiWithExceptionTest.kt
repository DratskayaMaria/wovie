package com.example.wovie.integtarion.film

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.ui.actor.ActorViewModel
import com.example.wovie.utils.CoroutineRule
import com.example.wovie.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class GetActorDataFromApiWithExceptionTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private val apiService = Mockito.spy(ApiService::class.java)
    private lateinit var actorViewModel: ActorViewModel
    private val ERROR_MSG = "Something went wrong"

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        actorViewModel = ActorViewModel(apiService)
        `when`(apiService.getActorById(ACTOR_ID)).thenThrow(RuntimeException::class.java)
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        actorViewModel.getActorInfo(ACTOR_ID)
        advanceUntilIdle()
        val mes = actorViewModel.msg.getOrAwaitValue()
        Assert.assertEquals(mes, ERROR_MSG)
    }

    companion object {
        private const val ACTOR_ID = 1
    }
}

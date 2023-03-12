package com.example.wovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.main.MainViewModel
import com.example.wovie.utils.CoroutineRule
import com.example.wovie.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenGetDataWithInternetProblemTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var mainViewModel: MainViewModel
    private val apiService = Mockito.spy(ApiService::class.java)
    private val bookmarksRepository = Mockito.spy(BookmarkRepository::class.java)
    private val ERROR_MSG = ""

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        mainViewModel = MainViewModel(apiService, bookmarksRepository)
        `when`(apiService.getPopularMovies()).thenThrow(RuntimeException::class.java)
        `when`(bookmarksRepository.getAllBookmarks()).thenReturn(emptyList())
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        mainViewModel.getFilms()
        advanceUntilIdle()
        val mes = mainViewModel.msg.getOrAwaitValue()
        Assert.assertEquals(mes, ERROR_MSG)
    }
}
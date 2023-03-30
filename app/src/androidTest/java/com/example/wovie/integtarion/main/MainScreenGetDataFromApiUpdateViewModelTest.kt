package com.example.wovie.integtarion.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.*
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
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenGetDataFromApiUpdateViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()
    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var db: DatabaseService
    private val apiService = Mockito.spy(ApiService::class.java)

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
        mainViewModel = MainViewModel(apiService, bookmarksRepository)

        `when`(apiService.getNowPlayingMovies()).thenReturn(getNowPlaying())
        `when`(apiService.getPopularMovies()).thenReturn(getPopular())
        `when`(apiService.getTopRatedMovies()).thenReturn(getTopRated())
        `when`(apiService.getUpcomingMovies()).thenReturn(getUpcoming())
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        mainViewModel.getFilms()
        advanceUntilIdle()

        val nowPlaying = mainViewModel.nowPlayingMutableLiveData.getOrAwaitValue()
        Assert.assertEquals(nowPlaying, getNowPlaying().results)
        val popular = mainViewModel.popularMutableLiveData.getOrAwaitValue()
        Assert.assertEquals(popular, getPopular().results)
        val topRated = mainViewModel.topRatedMutableLiveData.getOrAwaitValue()
        Assert.assertEquals(topRated, getTopRated().results)
        val upcoming = mainViewModel.upcomingMutableLiveData.getOrAwaitValue()
        Assert.assertEquals(upcoming, getUpcoming().results)
    }

    private fun getNowPlaying() =
        NowPlaying(
            dates = null,
            page = 0,
            results = null,
            total_pages = 0,
            total_results = 0,
        )

    private fun getPopular() =
        Popular(
            page = 0,
            results = null,
            total_pages = 0,
            total_results = 0,
        )

    private fun getTopRated() =
        TopRated(
            page = 0,
            results = null,
            total_pages = 0,
            total_results = 0,
        )

    private fun getUpcoming() =
        Upcoming(
            page = 0,
            results = null,
            total_pages = 0,
            total_results = 0,
        )

}
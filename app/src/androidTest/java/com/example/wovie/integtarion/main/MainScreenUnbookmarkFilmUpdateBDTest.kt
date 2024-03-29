package com.example.wovie.integtarion.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.main.MainViewModel
import com.example.wovie.ui.model.Film
import com.example.wovie.utils.CoroutineRule
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenUnbookmarkFilmUpdateBDTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var db: DatabaseService
    private val apiService = Mockito.mock(ApiService::class.java)

    private val filmMock = getFilmMock()

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
        mainViewModel = MainViewModel(apiService, bookmarksRepository)

        addPreparedDataInDB()
        advanceUntilIdle()
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        mainViewModel.setBookMarkStatus(filmMock)
        advanceUntilIdle()
        val list = bookmarksRepository.getAllBookmarks()
        Assert.assertEquals(0, list.size)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun getFilmMock() = Film(
        FILM_ID,
        null,
        null,
        null,
        0.0,
        0,
        "",
        "",
        null,
        true
    )

    companion object {
        private const val FILM_ID = 1
    }

    private suspend fun addPreparedDataInDB() {
        bookmarksRepository.insertBookmarkedMovie(FILM_ID)
    }
}
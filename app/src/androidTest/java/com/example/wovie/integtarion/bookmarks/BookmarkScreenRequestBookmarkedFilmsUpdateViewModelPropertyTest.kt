package com.example.wovie.integtarion.bookmarks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.FilmResponse
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.bookmarks.BookmarksViewModel
import com.example.wovie.utils.CoroutineRule
import com.example.wovie.utils.getOrAwaitValue
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
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkScreenRequestBookmarkedFilmsUpdateViewModelPropertyTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var bookmarksViewModel: BookmarksViewModel
    private lateinit var db: DatabaseService
    private val apiService = Mockito.spy(ApiService::class.java)

    private val bookmarkedFilmsIds = listOf(1, 2, 3, 4)

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
        bookmarksViewModel = BookmarksViewModel(bookmarksRepository, apiService)

        bookmarkedFilmsIds.forEach { `when`(apiService.getMovieById(it)).thenReturn(getFilmResponseMock(it)) }

        addPreparedDataInDB()
        advanceUntilIdle()
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        bookmarksViewModel.getData()
        advanceUntilIdle()
        val results = bookmarksViewModel.bookMarkResults.getOrAwaitValue().map { it.filmId }
        Assert.assertTrue(results == bookmarkedFilmsIds)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private suspend fun addPreparedDataInDB() {
        bookmarkedFilmsIds.forEach { bookmarksRepository.insertBookmarkedMovie(it) }
    }

    private fun getFilmResponseMock(id: Int) =
        FilmResponse(
            backdrop_path = null,
            genre_ids = listOf(),
            id = id,
            original_language = "",
            original_title = "",
            overview = "",
            popularity = 0.0,
            poster_path = null,
            title = "",
            release_date = "",
            video = false,
            vote_average = 0.0,
            vote_count = 0
        )
}
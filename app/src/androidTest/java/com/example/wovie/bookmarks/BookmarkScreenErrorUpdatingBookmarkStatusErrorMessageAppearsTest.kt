package com.example.wovie.bookmarks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.ui.bookmarks.BookmarksViewModel
import com.example.wovie.ui.model.Film
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
class BookmarkScreenErrorUpdatingBookmarkStatusErrorMessageAppearsTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksViewModel: BookmarksViewModel
    private val apiService = Mockito.mock(ApiService::class.java)
    private val bookmarksRepository = Mockito.spy(BookmarkRepository::class.java)

    private val filmMock = getFilmMock()

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        bookmarksViewModel = BookmarksViewModel(bookmarksRepository, apiService)

        `when`(bookmarksRepository.insertBookmarkedMovie(FILM_ID)).thenThrow(RuntimeException::class.java)
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        bookmarksViewModel.setBookMarkStatus(filmMock)
        advanceUntilIdle()
        val result = bookmarksViewModel.msg.getOrAwaitValue()
        val msg = "operation failed"
        Assert.assertEquals(result, msg)
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
        false
    )

    companion object {
        private const val FILM_ID = 1
    }
}
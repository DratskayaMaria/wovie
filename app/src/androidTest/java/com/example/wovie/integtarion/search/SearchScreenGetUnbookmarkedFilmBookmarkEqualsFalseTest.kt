package com.example.wovie.integtarion.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.api.response.FilmResponse
import com.example.wovie.api.response.SearchResponse
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.search.SearchViewModel
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
class SearchScreenGetUnbookmarkedFilmBookmarkEqualsFalseTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var db: DatabaseService
    private val apiService = Mockito.spy(ApiService::class.java)

    @Before
    fun before() = runTest(StandardTestDispatcher()) {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
        searchViewModel = SearchViewModel(apiService, bookmarksRepository)

        `when`(apiService.getSearchResults(QUERY)).thenReturn(getSearchResponseMock())
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        searchViewModel.getSearchResults(QUERY, true)
        advanceUntilIdle()
        val result = searchViewModel.searchResultMutableLiveData
            .getOrAwaitValue()
            .first { it.filmId == FILM_ID }
        Assert.assertFalse(result.isBookmarked)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
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

    private fun getSearchResponseMock() =
        SearchResponse (
            page = 1,
            results = listOf(getFilmResponseMock(FILM_ID)),
            total_pages = 1,
            total_results = 1
        )

    companion object {
        private const val QUERY = "query"
        private const val FILM_ID = 1
    }

}
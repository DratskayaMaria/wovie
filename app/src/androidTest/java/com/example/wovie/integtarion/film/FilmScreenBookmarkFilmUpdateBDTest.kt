package com.example.wovie.integtarion.film

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.film.FilmViewModel
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
class FilmScreenBookmarkFilmUpdateBDTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var filmViewModel: FilmViewModel
    private lateinit var db: DatabaseService
    private val apiService = Mockito.mock(ApiService::class.java)

    private val filmMock = getFilmMock()

    @Before
    fun before() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
        filmViewModel = FilmViewModel(apiService, bookmarksRepository)
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        filmViewModel.setBookMarkStatus(filmMock)
        advanceUntilIdle()
        val list = bookmarksRepository.getAllBookmarks()
        Assert.assertNotNull(list.find { it.movieId == FILM_ID })
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
        false
    )

    companion object {
        private const val FILM_ID = 1
    }
}
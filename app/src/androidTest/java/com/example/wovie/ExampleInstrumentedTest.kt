package com.example.wovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import com.example.wovie.ui.model.Film
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import kotlinx.coroutines.test.runTest
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleInstrumentedTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineRule()

    private lateinit var bookmarksRepository: BookmarkRepository
    private lateinit var db: DatabaseService
    private val apiService = Mockito.mock(ApiService::class.java)

    private val filmMock = getFilmMock()

    @Before
    fun before() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), DatabaseService::class.java).build()
        bookmarksRepository = BookmarkRepositoryImpl(db)
    }

    @Test
    fun test(): Unit = runTest(StandardTestDispatcher()) {
        bookmarksRepository.insertBookmarkedMovie(FILM_ID)
        val list = bookmarksRepository.getAllBookmarks()
        assertNotNull(list.find { it.movieId == FILM_ID })
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
}
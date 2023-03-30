package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckDeleteFilmFromMainScreen {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    val mainScreen = MainScreen(activityRule)

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
        mainScreen.addFilmInBookmarks(0, mainScreen.isFilmBookmarkedByPos(0))
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun test() {
        val deletedFilm = mainScreen.getFilmTitleByPos(0)
        mainScreen
            .deleteFirstFilmFromBookmarks()
            .clickOnBookmarkInAppBar()
            .checkFilmDoesNotExist(deletedFilm)
    }
}

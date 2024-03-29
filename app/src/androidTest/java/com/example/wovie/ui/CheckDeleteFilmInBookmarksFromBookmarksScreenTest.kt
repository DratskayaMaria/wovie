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
class CheckDeleteFilmInBookmarksFromBookmarksScreenTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)
    var filmTitle = ""

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
        val mainScreen = MainScreen(activityRule)
        mainScreen
            .clickOnBookmarkInAppBar()
            .clickDeleteButton()
            .clickOnBackButton()
        val filmScreen = MainScreen(activityRule).addFilmInBookmarks(0, false)
            .clickOnFirstFilm()
        filmTitle = filmScreen.getFilmTitle(activityRule)
        filmScreen.clickOnBackButtonToMainScreen()

    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkDeleteFilmInBookmarksFromBookmarksScreenTest() {
        MainScreen(activityRule)
            .clickOnBookmarkInAppBar()
            .checkScreenTitle()
            .checkFilmExist(filmTitle)
            .clickOnBookmarkButtonOnFilmPosition(0)
            .clickOnBackButton()
        MainScreen(activityRule)
            .clickOnBookmarkInAppBar()
            .checkFilmDoesNotExist(filmTitle)
    }
}
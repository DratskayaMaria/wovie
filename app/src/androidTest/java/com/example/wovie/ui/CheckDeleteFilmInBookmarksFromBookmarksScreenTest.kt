package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
@Ignore
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
    fun checkDeleteFilmInBookmarksFromFilmScreen() {
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
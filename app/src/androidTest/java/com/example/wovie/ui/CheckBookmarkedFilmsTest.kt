package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckBookmarkedFilmsTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)
    val mainScreen = MainScreen(activityRule)

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
        mainScreen
            .clickOnBookmarkInAppBar()
            .clickDeleteButton()
            .clickOnBackButton()
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkDeleteAllBookmarksTest() {
        val filmTitleFromMainScreen = mainScreen.getFilmTitleByPos(1)

        mainScreen
            .addFilmInBookmarks(1, false)

        val filmScreen = mainScreen.clickOnFirstFilm()
        val filmTitleFromFilmScreen = filmScreen.getFilmTitle(activityRule)

        filmScreen
            .addFilmInBookmark()
            .clickOnBackButtonToMainScreen()
        val afterEditingMainScreen = MainScreen(activityRule)
        afterEditingMainScreen
            .clickOnBookmarkInAppBar()
            .checkScreenTitle()
            .checkFilmExist(filmTitleFromMainScreen)
            .checkFilmExist(filmTitleFromFilmScreen)
            .checkCard()
    }
}
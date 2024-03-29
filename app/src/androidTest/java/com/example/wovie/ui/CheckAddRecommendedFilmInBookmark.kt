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
class CheckAddRecommendedFilmInBookmark {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkAddRecommendedFilmInBookmark() {
        val mainScreen = MainScreen(activityRule)

        val filmScreen = mainScreen.clickOnFirstFilm()

        filmScreen
            .clickOnFirstRecommendedFilm()

        val recommendedFilmTitle = filmScreen.getFilmTitle(activityRule)

        filmScreen
            .addFilmInBookmark()
            .clickOnBackButtonToFilmScreen()
            .clickOnBackButtonToMainScreen()
        val afterEditingMainScreen = MainScreen(activityRule)
        afterEditingMainScreen
            .clickOnBookmarkInAppBar()
            .checkScreenTitle()
            .checkFilmExist(recommendedFilmTitle)
    }
}
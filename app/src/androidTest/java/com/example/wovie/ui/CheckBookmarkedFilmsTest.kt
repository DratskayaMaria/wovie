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

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkDeleteAllBookmarksTest() {
        /*val mainScreen = MainScreen()
        mainScreen
            .addFilmInBookmarks(0)
            .clickOnBookmarkInAppBar(activityRule)
            
            .addFilmInBookmarks(0)
            .clickOnBookmarkInAppBar()*/

    }
}
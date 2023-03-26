package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckDeleteFilmFromBookmarksTest {

    var activityRule = ActivityTestRule(MainActivity::class.java)
    lateinit var mainScreen: MainScreen
    @Before
    fun before() {
        mainScreen = MainScreen(activityRule)
        mainScreen.addFilmInBookmarks(0, mainScreen.isFilmBookmarkedByPos(0))
    }

    @Test
    fun checkDeleteFilmFromBookmarks() {
        mainScreen
            .deleteFirstFilmFromBookmarks()
            .checkFirstFilmBookmarkedFlag(false)
        val firstFilmTitle = mainScreen.getFilmTitleByPos(0)
        mainScreen
            .clickOnBookmarkInAppBar()
            .checkScreenTitle()
            .checkFilmExist(firstFilmTitle)
    }
}
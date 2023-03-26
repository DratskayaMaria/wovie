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

    @Before
    fun before() {
        val mainScreen = MainScreen()
        mainScreen.addFirstFilmInBookmarks()
    }

    @Test
    fun checkDeleteFilmFromBookmarks() {
        val mainScreen = MainScreen()
        mainScreen.deleteFirstFilmFromBookmarks()
        mainScreen.checkFirstFilmBookmarkedFlag(false)
        mainScreen.clickOnBookmarkInAppBar(activityRule)
            .checkScreenTitle()
            .checkFilmExist("fdvdf")
    }
}
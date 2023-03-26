package com.example.wovie.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckAddFilmInBookmarksTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkAddFilmInBookmarks() {
        val mainScreen = MainScreen()
        mainScreen.addFirstFilmInBookmarks()
        mainScreen.checkFirstFilmBookmarkedFlag(true)
        mainScreen.clickOnBookmarkInAppBar(activityRule)
            .checkScreenTitle()
            .isDeleteButtonVisible()
            .checkFilmExist("fdvdf")
    }
}
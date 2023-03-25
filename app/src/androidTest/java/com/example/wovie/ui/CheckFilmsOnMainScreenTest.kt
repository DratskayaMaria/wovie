package com.example.wovie.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckFilmsOnMainScreenTest {
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkFilmsOnMainScreen() {
        val mainScreen = MainScreen()
        mainScreen
            .checkNowPlayingVisible()
            .checkPopularVisible()
            .checkTopRatedVisible()
            .checkUpcomingVisible()
    }
}

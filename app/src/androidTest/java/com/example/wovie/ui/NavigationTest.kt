package com.example.wovie.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.wovie.R
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun mainActivityTest() {
        val mainScreen = MainScreen(activityRule)
        mainScreen
            .clickOnSearchOnAppBar()
            .checkScreenTitle()
            .clickOnBackButton()
            .clickOnBookmarkInAppBar()
            .checkScreenTitle()
            .clickOnBackButton()
            .clickOnFirstFilm()
            .clickOnFirstActor()
            .checkTitle()
            .clickOnBackButton()
            .clickOnFirstRecommendedFilm()
            .clickOnBackButtonToFilmScreen()
            .clickOnBackButtonToMainScreen()
    }
}

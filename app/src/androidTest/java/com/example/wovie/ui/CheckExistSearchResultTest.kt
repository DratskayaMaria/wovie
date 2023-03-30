package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.ui.screens.SearchScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@Ignore
@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckExistSearchResultTest {
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
    fun checkExistSearchResultTest() {
        val searchRequest = "Titanic"
        val mainScreen = MainScreen(activityRule)
        mainScreen
            .clickOnSearchOnAppBar()
            .enterSearchRequest(searchRequest)
            .checkNameOfFirstSearchResult(searchRequest)
            .checkCardContent()
    }
}

package com.example.wovie.ui

import androidx.test.InstrumentationRegistry
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

@RunWith(AndroidJUnit4::class)
@LargeTest
class NoInternetConnectionTest {
    @get:Rule var activityRule = ActivityTestRule(MainActivity::class.java)
    @Before
    fun before() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data disable")
    }

    @After
    fun after() {
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc data enable")
    }

    @Test
    fun noInternetConnectionTest() {
        val searchRequest = "qwqweqwewdsxsdwewefewfw"
        val mainScreen = MainScreen(activityRule)
        mainScreen
            .clickOnSearchOnAppBar()
            .enterSearchRequest(searchRequest)
            .checkNoInternetVisible()
    }
}
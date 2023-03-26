package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckActorFromFilmScreenTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkFilmScreenFromMainScreenTest() {
        val mainScreen = MainScreen()
        mainScreen
            .clickOnFirstFilm()
            .clickOnFirstActor()
            .checkTitle()
            .checkPhoto()
            .checkName()
            .checkYearsOfLife()
            .checkBio()
    }
}
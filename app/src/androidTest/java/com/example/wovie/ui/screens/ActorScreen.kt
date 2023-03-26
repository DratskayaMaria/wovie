package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity

class ActorScreen(private val activityRule: ActivityTestRule<MainActivity>) {
    fun clickOnBackButton(): FilmScreen {
        Espresso.onView(withId(R.id.back_button))
            .perform(ViewActions.click())

        return FilmScreen(activityRule)
    }

    fun checkTitle() : ActorScreen{
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(ViewMatchers.withText("Actor")))
        return this
    }

    fun checkPhoto(): ActorScreen {
        onView(withId(R.id.poster_image))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkName(): ActorScreen {
        onView(withId(R.id.name))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkYearsOfLife() : ActorScreen{
        onView(withId(R.id.birth_year))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.death_year))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkBio() : ActorScreen{
        onView(withId(R.id.biography))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }
}
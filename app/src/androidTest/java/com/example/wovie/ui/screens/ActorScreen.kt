package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.wovie.R

class ActorScreen {
    fun checkTitle() {
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun checkPhoto() {
        onView(withId(R.id.poster_image))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun checkName() {
        onView(withId(R.id.name))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun checkYearsOfLife() {
        onView(withId(R.id.birth_year))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.death_year))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun checkBio() {
        onView(withId(R.id.biography))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}
package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.wovie.R

class SearchScreen {
    fun checkScreenTitle() {
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(withText("Search")))
    }
}
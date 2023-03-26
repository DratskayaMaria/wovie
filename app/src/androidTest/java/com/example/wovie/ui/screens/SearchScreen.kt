package com.example.wovie.ui.screens

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.wovie.R
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.search.SearchViewHolder
import com.example.wovie.ui.utils.RecyclerViewMatcher

class SearchScreen {
    fun checkScreenTitle() {
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(withText("Search")))
    }

    fun clickOnFirstFilm(): FilmScreen {
        onView(withId(R.id.search_results))
            .perform(
                RecyclerViewActions
                .actionOnItemAtPosition<SearchViewHolder>(0, ViewActions.click()))
        return FilmScreen()
    }

    fun enterSearchRequest(text: String): SearchScreen {
        onView(withId(R.id.search_view))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(text))
        return this
    }

    fun checkNameOfFirstSearchResult(text: String) {
        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.search_result_holder))
            .check(ViewAssertions.matches(withText(text)))
    }
}
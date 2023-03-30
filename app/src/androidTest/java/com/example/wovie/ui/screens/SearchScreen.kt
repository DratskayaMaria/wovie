package com.example.wovie.ui.screens

import android.view.KeyEvent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity
import com.example.wovie.ui.search.SearchViewHolder
import com.example.wovie.ui.utils.RecyclerViewMatcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not


class SearchScreen(private val activityRule: ActivityTestRule<MainActivity>) {
    fun clickOnBackButton(): MainScreen {
        Espresso.onView(withId(R.id.back_button))
            .perform(scrollTo())
            .perform(ViewActions.click())

        return MainScreen(activityRule)
    }

    fun checkScreenTitle() : SearchScreen{
        onView(withId(R.id.top_title))
            .perform(scrollTo())
            .check(ViewAssertions.matches(withText("Search")))
        return this
    }

    fun clickOnFirstFilm(): FilmScreen {
        onView(withId(R.id.search_results))
            .perform(
                RecyclerViewActions
                .actionOnItemAtPosition<SearchViewHolder>(0, ViewActions.click()))
        return FilmScreen(activityRule)
    }

    fun enterSearchRequest(text: String): SearchScreen {
        onView(withId(R.id.search_view))
            .perform(ViewActions.click())
        onView(withId(R.id.search_src_text))
            .perform(typeText(text), pressKey(KeyEvent.KEYCODE_ENTER))
        return this
    }

    fun checkNameOfFirstSearchResult(text: String): SearchScreen {
        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.title))
            .check(ViewAssertions.matches(withText(text)))
        return this
    }

    fun checkNoResultsVisible() {
        onView(withId(R.id.no_results_layout))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun checkCardContent() {
        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.title))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.result_image))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.rating))
            .check(ViewAssertions.matches(isDisplayed()))
    }

}
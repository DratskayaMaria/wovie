package com.example.wovie.ui.screens

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.wovie.R
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.search.SearchViewHolder
import com.example.wovie.ui.utils.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

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
        onView(withId(R.id.search_src_text))
            .perform(typeText(text))
        return this
    }

    fun checkNameOfFirstSearchResult(text: String) {
        onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.search_result_holder))
            .check(ViewAssertions.matches(withText(text)))
    }

//    private fun typeSearchViewText(text: String): ViewAction {
//        return object : ViewAction {
//            override fun getDescription(): String {
//                return "Change view text"
//            }
//
//            override fun getConstraints(): Matcher<View> {
//                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
//            }
//
//            override fun perform(uiController: UiController?, view: View?) {
//                (view as SearchView).setQuery(text, true)
//            }
//        }
//    }

}
package com.example.wovie.ui.screens

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity
import com.example.wovie.ui.utils.RecyclerViewMatcher

class BookmarksScreen(private val activityRule: ActivityTestRule<MainActivity>) {
    fun clickOnBackButton(): MainScreen {
        onView(withId(R.id.back_button))
            .perform(ViewActions.click())

        return MainScreen()
    }

    fun checkScreenTitle(): BookmarksScreen {
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(ViewMatchers.withText("Watchlist")))
        return this
    }

    fun checkFilmExist(nameFilm: String?): BookmarksScreen {
        val count = activityRule.activity.findViewById<RecyclerView>(R.id.bookmarks_recyclerview).adapter?.itemCount
        for(i in 0..count!!) {
            onView(RecyclerViewMatcher(R.id.bookmarks_recyclerview)
                .atPositionOnView(i, R.id.title))
                .check(ViewAssertions.matches(withText(nameFilm)));
        }
        return this
    }

    fun isDeleteButtonVisible(): BookmarksScreen {
        onView(withId(R.id.delete_icon))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun clickDeleteButton() {
        Espresso.onView(ViewMatchers.withId(R.id.delete_icon))
            .perform(ViewActions.click())
    }
}
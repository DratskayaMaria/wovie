package com.example.wovie.ui.screens

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.utils.RecyclerViewItemCountAssertion
import com.example.wovie.ui.utils.RecyclerViewMatcher
import com.example.wovie.ui.utils.withDrawable
import org.hamcrest.Matcher

class MainScreen : BaseScreen() {
    fun clickOnBookmarkInAppBar(activity: ActivityTestRule<MainActivity>): BookmarksScreen {
        onView(withId(R.id.book_marks))
            .perform(ViewActions.click())

        return BookmarksScreen(activity)
    }

    fun clickOnSearchOnAppBar(): SearchScreen {
        onView(withId(R.id.search_view))
            .perform(ViewActions.click())

        return SearchScreen()
    }

    fun clickOnFirstFilm(): FilmScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(0, click()))
        return FilmScreen()
    }

    fun checkNowPlayingVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkPopularVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkTopRatedVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkUpcomingVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun addFirstFilmInBookmarks(): String {
        onView(RecyclerViewMatcher(R.id.now_playing_recyclerview)
            .atPositionOnView(0, R.id.book_mark))
            .perform(click())

        return onView(RecyclerViewMatcher(R.id.search_results)
            .atPositionOnView(0, R.id.title)).toString()
    }


    fun deleteFirstFilmFromBookmarks() {
        onView(RecyclerViewMatcher(R.id.now_playing_recyclerview)
            .atPositionOnView(0, R.id.book_mark))
            .perform(click())
    }

    fun checkFirstFilmBookmarkedFlag(isBookmarked: Boolean) {
        onView(RecyclerViewMatcher(R.id.now_playing_recyclerview)
            .atPositionOnView(0, R.id.book_mark))
            .check(ViewAssertions.matches(isBookmarked(isBookmarked)))
    }

    private fun isBookmarked(isBookmarked: Boolean): Matcher<View> {
        return if (isBookmarked) {
            withDrawable(R.drawable.bookmark)
        } else {
            withDrawable(R.drawable.empty_bookmark)
        }
    }

    companion object {
        private const val FILMS_COUNT = 20
    }
}

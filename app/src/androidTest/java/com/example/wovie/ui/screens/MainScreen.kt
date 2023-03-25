package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.wovie.R
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.utils.RecyclerViewItemCountAssertion
import com.example.wovie.ui.utils.waitForView
import com.example.wovie.ui.utils.waitForViewDisplayed

class MainScreen: BaseScreen() {
    fun clickOnBookmarkInAppBar(): BookmarksScreen {
        onView(withId(R.id.book_marks))
            .perform(ViewActions.click())

        return BookmarksScreen()
    }

    fun clickOnSearchOnAppBar(): SearchScreen {
        onView(withId(R.id.search_view))
            .perform(ViewActions.click())

        return SearchScreen()
    }

    fun clickOnFirstFilm(): FilmScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmViewHolder>(0, click()))
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

    companion object {
        private const val FILMS_COUNT = 20
    }
}

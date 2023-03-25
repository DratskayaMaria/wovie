package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
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
        TODO()
    }

    fun clickOnSearchOnAppBar(): SearchScreen {
        TODO()
    }

    fun clickOnFirstFilm(): FilmScreen {
        TODO()
    }

    fun waitForLoader(): MainScreen {
        waitForViewDisplayed(withId(R.id.progressbar), isRoot())
        return this
    }

    fun checkNowPlayingVisible(): MainScreen {
        waitForLoader()
        waitForViewDisplayed(withId(R.id.now_playing_recyclerview))
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmViewHolder>(0, waitForView(hasDescendant(withId(R.id.title)), 20000)))

        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkPopularVisible(): MainScreen {
        waitForLoader()
        waitForViewDisplayed(withId(R.id.now_playing_recyclerview), isRoot())
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmViewHolder>(0, waitForView(hasDescendant(withId(R.id.title)), 20000)))
        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkTopRatedVisible(): MainScreen {
        waitForLoader()
        waitForViewDisplayed(withId(R.id.progressbar), isRoot())
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmViewHolder>(0, waitForView(hasDescendant(withId(R.id.title)), 20000)))

        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkUpcomingVisible(): MainScreen {
        waitForLoader()
        waitForViewDisplayed(withId(R.id.upcoming_recyclerview), isRoot())
        onView(withId(R.id.now_playing_recyclerview))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmViewHolder>(0, waitForView(hasDescendant(withId(R.id.title)), 20000)))

        onView(withId(R.id.now_playing_recyclerview))
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    companion object {
        private const val FILMS_COUNT = 20
    }
}

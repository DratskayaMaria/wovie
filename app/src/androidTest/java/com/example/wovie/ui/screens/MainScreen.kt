package com.example.wovie.ui.screens

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.utils.RecyclerViewItemCountAssertion
import com.example.wovie.ui.utils.RecyclerViewMatcher
import com.example.wovie.ui.utils.withDrawable
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not

class MainScreen() {

    private var activityRule: ActivityTestRule<MainActivity>? = null

    constructor(activityRule: ActivityTestRule<MainActivity>) : this() {
       this.activityRule = activityRule
    }

    fun getFilmTitleByPos(pos: Int): String? {
        return getRecyclerById(R.id.now_playing_recyclerview)
            ?.findViewHolderForAdapterPosition(pos)
            ?.itemView
            ?.findViewById<TextView>(R.id.title)
            ?.text
            ?.toString()
    }

    fun isFilmBookmarkedByPos(pos: Int): Boolean {
        val res = getRecyclerById(R.id.now_playing_recyclerview)
            ?.findViewHolderForAdapterPosition(pos)
            ?.itemView
            ?.findViewById<ImageView>(R.id.book_mark)
            ?.sourceLayoutResId

        return res == R.drawable.bookmark
    }

    private fun getRecyclerById(id: Int): RecyclerView? {
        return activityRule?.activity?.findViewById<RecyclerView>(id)
    }

    fun clickOnBookmarkInAppBar(): BookmarksScreen {
        onView(withId(R.id.book_marks))
            .perform(ViewActions.click())

        return BookmarksScreen(activityRule!!)
    }

    fun clickOnSearchOnAppBar(): SearchScreen {
        onView(withId(R.id.search_view))
            .perform(ViewActions.click())

        return SearchScreen()
    }

    fun clickOnFirstFilm(): FilmScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(scrollTo())
            .perform(RecyclerViewActions.actionOnItemAtPosition<FilmViewHolder>(0, click()))
        return FilmScreen()
    }

    fun addFilmInBookmarks(position: Int, isAlreadyBookmarked: Boolean): MainScreen {
        if (isAlreadyBookmarked) return this
        onView(RecyclerViewMatcher(R.id.now_playing_recyclerview)
            .atPositionOnView(position, R.id.book_mark))
            .perform(click())
        return this
    }

    fun checkNowPlayingVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(scrollTo())
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkPopularVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(scrollTo())
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkTopRatedVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(scrollTo())
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }

    fun checkUpcomingVisible(): MainScreen {
        onView(withId(R.id.now_playing_recyclerview))
            .perform(scrollTo())
            .check(RecyclerViewItemCountAssertion(FILMS_COUNT))
        return this
    }


    fun deleteFirstFilmFromBookmarks(): MainScreen {
        onView(
            RecyclerViewMatcher(R.id.now_playing_recyclerview)
                .atPositionOnView(0, R.id.book_mark)
        )
            .perform(click())
        return this
    }

    fun checkFirstFilmBookmarkedFlag(isBookmarked: Boolean): MainScreen {
        onView(
            RecyclerViewMatcher(R.id.now_playing_recyclerview)
                .atPositionOnView(0, R.id.book_mark)
        )
            .check(ViewAssertions.matches(isBookmarked(isBookmarked)))
        return this
    }

    private fun isBookmarked(isBookmarked: Boolean): Matcher<View> {
        return if (isBookmarked) {
            withDrawable(R.drawable.bookmark)
        } else {
            withDrawable(R.drawable.empty_bookmark)
        }
    }

    fun checkCardInNowPlayingContent(): MainScreen {
        checkCard(R.id.now_playing_recyclerview)
        return this
    }

    fun checkCardInPopularContent(): MainScreen {
        checkCard(R.id.popular_recyclerview)
        return this
    }

    fun checkCardInTopRatedContent(): MainScreen {
        checkCard(R.id.top_rated_recyclerview)
        return this
    }

    fun checkCardInUpcomingContent(): MainScreen {
        checkCard(R.id.upcoming_recyclerview)
        return this
    }

    private fun checkCard(recyclerId: Int) {
        onView(RecyclerViewMatcher(recyclerId).atPositionOnView(0, R.id.poster))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))

        onView(RecyclerViewMatcher(recyclerId).atPositionOnView(0, R.id.rating))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))

        onView(RecyclerViewMatcher(recyclerId).atPositionOnView(0, R.id.title))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun isNoInternetMessageDisplayed() {
        onView(withText("No internet connection")).inRoot(
            withDecorView(
                not(
                    `is`(
                        activityRule?.getActivity()?.getWindow()?.getDecorView()
                    )
                )
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

    companion object {
        private const val FILMS_COUNT = 20
    }


}


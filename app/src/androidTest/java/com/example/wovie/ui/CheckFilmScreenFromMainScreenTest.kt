package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.wovie.ui.screens.FilmScreen
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckFilmScreenFromMainScreenTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(IdlingResource.countingIdlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(IdlingResource.countingIdlingResource)
    }

    @Test
    fun checkFilmScreenFromMainScreenTest() {
        val mainScreen = MainScreen(activityRule)

        mainScreen.addFilmInBookmarks(0, mainScreen.isFilmBookmarkedByPos(0))
        val filmTitle = mainScreen.getFilmTitleByPos(0)

        mainScreen
            .clickOnFirstFilm()
            .checkFilmTitle(filmTitle)
            .checkFilmCover()
            .checkFilmPoster()
            .checkFilmGenresHeading()
            .checkFilmGenres()
            .checkFilmDateTitle()
            .checkFilmDate()
            .checkFilmRatingHeading()
            .checkFilmRating()
            .checkFilmVotersHeading()
            .checkFilmVoters()
            .checkFilmOverviewHeading()
            .checkFilmOverview()
            .checkFilmActorsTitle()
            .checkFilmActorsList()
            .checkFilmRecommendedTitle()
            .checkFilmRecommendedList()
    }
}
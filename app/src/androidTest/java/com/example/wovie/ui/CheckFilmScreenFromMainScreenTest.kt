package com.example.wovie.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.wovie.ui.screens.FilmScreen
import com.example.wovie.ui.screens.MainScreen
import com.example.wovie.util.IdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CheckFilmScreenFromMainScreenTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

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
        val mainScreen = MainScreen()
        val filmTitle = mainScreen.getFilmTitleByPos(0)
        val filmRating = mainScreen.getFilmRatingByPos(0)
        val filmPoster = mainScreen.getFilmPosterByPos(0)

        mainScreen
            .clickOnFirstFilm()
            .checkFilmTitle(filmTitle)
            .checkFilmCover()
            //.checkFilmPoster(filmPoster)
            .checkFilmGenresHeading()
            .checkFilmGenres()
            .checkFilmDateTitle()
            .checkFilmDate()
            .checkFilmRatingHeading()
            //.checkFilmRating(filmRating)
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
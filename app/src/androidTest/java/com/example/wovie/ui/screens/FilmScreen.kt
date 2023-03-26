package com.example.wovie.ui.screens

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.wovie.R
import com.example.wovie.ui.film.ActorCardViewHolder
import com.example.wovie.ui.film.FilmCardViewHolder
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import betterScrollTo
import com.example.wovie.ui.MainActivity

class FilmScreen {
    fun clickOnBookmark(activityRule: ActivityTestRule<MainActivity>) : BookmarksScreen {
        Espresso.onView(withId(R.id.bookmark))
            .perform(ViewActions.click())

        return BookmarksScreen(activityRule)
    }

    fun clickOnFirstActor() : ActorScreen {
        Espresso.onView(withId(R.id.actors_recycler))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<ActorCardViewHolder>(0, click()))
        return ActorScreen()
    }

    fun clickOnFirstRecommendedFilm() : FilmScreen {
        Espresso.onView(withId(R.id.recommended_recycler))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<FilmCardViewHolder>(0, click()))
        return FilmScreen()
    }

    fun checkFilmName() : FilmScreen {
        Espresso.onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmCover() : FilmScreen {
        Espresso.onView(withId(R.id.cover_image))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmPoster() : FilmScreen {
        Espresso.onView(withId(R.id.poster_image))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmGenresHeading() : FilmScreen {
        Espresso.onView(withId(R.id.genre_heading))
            .perform(betterScrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmGenres() : FilmScreen {
        Espresso.onView(withId(R.id.generes_recyclerview))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmDateTitle() : FilmScreen {
        Espresso.onView(withId(R.id.date_title))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmDate() : FilmScreen {
        Espresso.onView(withId(R.id.date))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmRatingHeading() : FilmScreen {
        Espresso.onView(withId(R.id.rating_heading))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmRating() : FilmScreen {
        Espresso.onView(withId(R.id.rating))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmVotersHeading() : FilmScreen {
        Espresso.onView(withId(R.id.voters_heading))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmVoters() : FilmScreen {
        Espresso.onView(withId(R.id.voters))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmOverviewHeading() : FilmScreen {
        Espresso.onView(withId(R.id.overview_heading))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmOverview() : FilmScreen {
        Espresso.onView(withId(R.id.overview))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmActorsTitle() : FilmScreen {
        Espresso.onView(withId(R.id.actors_title))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmActorsList() : FilmScreen {
        Espresso.onView(withId(R.id.actors_recycler))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmRecommendedTitle() : FilmScreen {
        Espresso.onView(withId(R.id.recommended_title))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

    fun checkFilmRecommendedList() : FilmScreen {
        Espresso.onView(withId(R.id.recommended_recycler))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        return this
    }

}
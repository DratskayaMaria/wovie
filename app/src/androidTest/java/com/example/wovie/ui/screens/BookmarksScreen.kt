package com.example.wovie.ui.screens

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.wovie.R
import com.example.wovie.ui.MainActivity
import com.example.wovie.ui.main.FilmViewHolder
import com.example.wovie.ui.utils.RecyclerViewMatcher
import java.util.EnumSet.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher

class BookmarksScreen(private val activityRule: ActivityTestRule<MainActivity>) {
    fun clickOnBackButton(): MainScreen {
        onView(withId(R.id.back_button))
            .perform(ViewActions.scrollTo())
            .perform(ViewActions.click())

        return MainScreen(activityRule)
    }

    fun checkScreenTitle(): BookmarksScreen {
        onView(withId(R.id.top_title))
            .check(ViewAssertions.matches(ViewMatchers.withText("Watchlist")))
        return this
    }

    fun checkFilmExist(nameFilm: String?): BookmarksScreen {
        val recycler = activityRule.activity
            .findViewById<RecyclerView>(R.id.bookmarks_recyclerview)
        val itemCount = recycler.adapter!!.itemCount
        matchChildViewByFilmName(nameFilm, R.id.title, withId(R.id.title))

        return this
    }

    fun checkCard() {
        onView(RecyclerViewMatcher(R.id.bookmarks_recyclerview).atPositionOnView(0, R.id.poster))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(RecyclerViewMatcher(R.id.bookmarks_recyclerview).atPositionOnView(0, R.id.rating))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    private fun matchChildViewByFilmName(filmName: String?, targetViewId: Int, itemMatcher: Matcher<View>): Matcher<View> =
        object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("Has view id $targetViewId and matches $itemMatcher for item with name")
            }

            public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val itemCount = recyclerView.adapter!!.itemCount
                for (i in 0 until itemCount) {
                    val holder = recyclerView.findViewHolderForAdapterPosition(i)
                    if (holder != null) {
                        val accountNameView = holder.itemView.findViewById<View>(R.id.title) as TextView
                        if (accountNameView.text == filmName) {
                            val targetView = holder.itemView.findViewById<View>(targetViewId)
                            return itemMatcher.matches(targetView)
                        }
                    }
                }
                return false
            }
        }

//    fun isDeleteButtonVisible(): BookmarksScreen {
//        onView(withId(R.id.delete_icon))
//            .check(ViewAssertions.matches(isDisplayed()))
//        return this
//    }

    fun checkFilmDoesNotExist(nameFilm: String?): BookmarksScreen {
        onView(RecyclerViewMatcher(R.id.bookmarks_recyclerview)
            .atPositionOnView(0, R.id.title))
            .check(ViewAssertions.doesNotExist());
        return this
    }
    private fun isDeleteButtonVisible(): Boolean {
        return activityRule.activity.findViewById<ImageView>(R.id.delete_icon)?.isVisible!!
    }

    fun isNoBookmarksVisible() {
        onView(withId(R.id.no_bookmarks_layout))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun clickDeleteButton(): BookmarksScreen {
        val isIconVisible = isDeleteButtonVisible()
        if (isIconVisible) {
                onView(withId(R.id.delete_icon))
                    .perform(ViewActions.click())
                clickYesButtonInAlertDialog()
            }
        return this
    }

    fun clickOnBookmarkButtonOnFilmPosition(position: Int): BookmarksScreen {
        onView(RecyclerViewMatcher(R.id.bookmarks_recyclerview)
            .atPositionOnView(position, R.id.book_mark))
            .perform(ViewActions.click())
        return this
    }

    private fun clickYesButtonInAlertDialog() {
        onView(withId(android.R.id.button1)).perform(ViewActions.click())
    }

}

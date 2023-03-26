package com.example.wovie.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.wovie.R
import com.example.wovie.ui.screens.MainScreen
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        MainScreen()
            .clickOnSearchOnAppBar()
            .checkScreenTitle()
            .clickOnBackButton()
            .clickOnBookmarkInAppBar(mActivityTestRule)
            .checkScreenTitle()
            .clickOnBackButton()
            .clickOnFirstFilm()
            .clickOnFirstActor()
            .checkTitle()
            .clickOnBackButton()
            .clickOnFirstRecommendedFilm()
            .clickOnBackButtonToFilmScreen()
            .clickOnBackButtonToMainScreen()
    }
}

package com.example.wovie.ui.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import junit.framework.AssertionFailedError
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Assert

/**
 * Visibility
 */

/*fun assertDisplayed(matcher: Matcher<View>) {
    tryToAssert(allOf(matcher, isDisplayed()), isDisplayed())
}

private fun tryToAssert(matcher: Matcher<View>, condition: Matcher<View>) {
    val assertion = matches(condition)
    onView(matcher)
        .withFailureHandler { _, _ ->

        }
}*/

fun assertNotDisplayed(matcher: Matcher<View>) {
    try {
        onView(matcher).check(doesNotExist())
    } catch (e: AssertionFailedError) {
        onView(matcher)
            .withFailureHandler { _, _ -> Assert.fail(e.message) }
            .check(matches(not(isDisplayed())))
    }
}

/*fun assertDisplayedWithId(id: Int) {
    assertDisplayed(withId(id))
}

fun assertText(text: String) {
    assertDisplayed(withText(text))
}*/

/**
 *
 */
/**
**
* This ViewAction tells espresso to wait till a certain view is found in the view hierarchy.
* @param viewId The id of the view to wait for.
* @param timeout The maximum time which espresso will wait for the view to show up (in milliseconds)
*/

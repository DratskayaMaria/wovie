package com.example.wovie.ui.utils

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.wovie.R
import java.util.concurrent.TimeUnit
import org.hamcrest.Matcher
import org.junit.Assert

fun waitForViewDisplayed(matcher: Matcher<View>, constraintMatcher: Matcher<View>? = null) {
    Espresso.onView(ViewMatchers.isRoot())
        .perform(waitForView(matcher, 20000, constraintMatcher))
}

private const val idlingResTimeout = 20L

fun waitForViewDisplayed(matcher: Matcher<View>, time: Long) {
    IdlingPolicies.setIdlingResourceTimeout(time, TimeUnit.SECONDS);
    waitForViewDisplayed(matcher)
    IdlingPolicies.setIdlingResourceTimeout(idlingResTimeout, TimeUnit.SECONDS);
}

private const val windowManagerName = "android.view.WindowManagerGlobal"
private const val viewsFieldString = "mViews"
private const val windowManagerString = "sDefaultWindowManager"

fun getRootViewForIdling(): View? {
    val windowManager = Class.forName(windowManagerName)
    val viewsField = windowManager.getDeclaredField(viewsFieldString)
    val instanceFields = windowManager.getDeclaredField(windowManagerString)
    viewsField.isAccessible = true
    instanceFields.isAccessible = true
    val instance = instanceFields.get(null)
    val views =  (viewsField.get(instance) as? ArrayList<View>)?.toArray<View>(emptyArray())
    Assert.assertNotNull(views)
    views?.isNotEmpty()?.let { Assert.assertTrue(it) }
    for (view in views!!) {
        if (view.hasWindowFocus()) return view
    }

    return null
}


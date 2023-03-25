package com.example.wovie.ui.utils

import android.view.View
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import org.mockito.internal.util.collections.Iterables

/*
class IdlingView(private val matcher: Matcher<View>): IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName() = matcher.toString()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun isIdleNow(): Boolean {
        val root = getRootViewForIdling() ?: return false
        val matchedViewIterator = Iterables.filter(TreeIterables.breadthFirstViewTraversal(root)) {
            matcher.matches(it)
        }.iterator()
        var matchedView: View? = null
        while (matchedViewIterator.hasNext()) {
            matchedView = matchedViewIterator.next()
        }

        return if (matchedView != null && matchedView.isShown) {
            callback?.onTransitionToIdle()
            true
        } else {
            false
        }
    }

}*/

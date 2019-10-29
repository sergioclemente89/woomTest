package com.clementecastillo.people.extension

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener

fun View.fadeIn(duration: Long = 200) {
    ViewCompat.animate(this).alpha(1f).setDuration(duration).setListener(
        object : ViewPropertyAnimatorListener {
            override fun onAnimationCancel(view: View) {}
            override fun onAnimationEnd(view: View) {}
            override fun onAnimationStart(view: View) {
                view.visibility = View.VISIBLE
            }
        }).start()
}

fun View.fadeOut(duration: Long = 200) {
    ViewCompat.animate(this).alpha(0f).setDuration(duration)
        .setListener(object : ViewPropertyAnimatorListener {
            override fun onAnimationStart(view: View) {}
            override fun onAnimationCancel(view: View) {}
            override fun onAnimationEnd(view: View) {
                view.visibility = View.GONE
            }
        }).start()
}

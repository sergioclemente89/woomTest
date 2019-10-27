package com.clementecastillo.people.extension

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun hideKeyboard(activity: Activity) {
    activity.currentFocus?.let {
        hideKeyboard(it)
    }
}

fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
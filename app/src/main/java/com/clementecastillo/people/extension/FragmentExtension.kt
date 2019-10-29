package com.clementecastillo.people.extension

import androidx.fragment.app.Fragment

fun Fragment.tagName(): String {
    return javaClass.simpleName
}

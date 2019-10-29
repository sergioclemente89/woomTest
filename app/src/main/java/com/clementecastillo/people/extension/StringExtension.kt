package com.clementecastillo.people.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
private val API_DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
@SuppressLint("SimpleDateFormat")
private val APP_DATE_FORMATTER = SimpleDateFormat("dd/MM/yyyy")

fun String.getFormattedDate(): String {
    try {
        API_DATE_FORMATTER.parse(this)?.let {
            return APP_DATE_FORMATTER.format(it)
        }
        return ""
    } catch (ignore: Exception) {
        return ""
    }
}
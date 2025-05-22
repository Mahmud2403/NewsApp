package com.example.newsapp.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun formatPubDate(pubDate: String): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    inputFormat.timeZone = TimeZone.getTimeZone("GMT")
    val date: Date = inputFormat.parse(pubDate) ?: return ""

    val now = Date()
    val diffMillis = now.time - date.time

    return if (diffMillis < TimeUnit.DAYS.toMillis(1)) {
        when {
            diffMillis < TimeUnit.MINUTES.toMillis(1) -> "just now"
            diffMillis < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
                "$minutes min ago"
            }
            else -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
                "$hours h ago"
            }
        }
    } else {
        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        outputFormat.format(date)
    }
}

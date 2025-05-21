package com.example.newsapp.util

import android.text.Html

fun stripHtml(html: String): String {
    return android.text.Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString().trim()
}

fun String.shorten(maxLength: Int): String {
    return if (this.length <= maxLength) this else this.substring(0, maxLength) + "…"
}
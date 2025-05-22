package com.example.newsapp.domain.model

data class NewsArticle(
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val author: String,
    val pubDate: String,
    val category: List<Category>,
)

data class Category(
    val domain: String? = null,
    val value: String = ""
)
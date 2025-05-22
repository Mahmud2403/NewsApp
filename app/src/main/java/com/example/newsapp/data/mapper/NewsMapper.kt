package com.example.newsapp.data.mapper

import com.example.newsapp.data.remote.model.CategoryDto
import com.example.newsapp.data.remote.model.ImageDto
import com.example.newsapp.data.remote.model.NewsItemDto
import com.example.newsapp.domain.model.Category
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.model.TopBar
import com.example.newsapp.util.shorten
import com.example.newsapp.util.stripHtml

fun NewsItemDto.toDomain(): NewsArticle =
    NewsArticle(
        title = title,
        description = description.let { stripHtml(it).shorten(300) },
        imageUrl = mediaContents.firstOrNull()?.url.orEmpty(),
        link = guid ?: link,
        author = creator.orEmpty(),
        pubDate = pubDate.orEmpty(),
        category = category.map { it.toCategory() }
    )


fun CategoryDto.toCategory(): Category =
    Category(
        value = value,
    )

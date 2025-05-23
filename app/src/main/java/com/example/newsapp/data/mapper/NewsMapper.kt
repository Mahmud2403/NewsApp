package com.example.newsapp.data.mapper

import com.example.newsapp.data.local.model.CategoryEntity
import com.example.newsapp.data.local.model.NewsArticleEntity
import com.example.newsapp.data.remote.model.CategoryDto
import com.example.newsapp.data.remote.model.NewsDto
import com.example.newsapp.data.remote.model.NewsItemDto
import com.example.newsapp.domain.model.Category
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.util.shorten
import com.example.newsapp.util.stripHtml

fun NewsItemDto.toNews(): NewsArticle =
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

fun NewsArticleEntity.toNews(): NewsArticle =
    NewsArticle(
        title = title,
        description = description,
        imageUrl = imageUrl,
        link = link,
        author = author,
        pubDate = pubDate,
        category = category.map { it.toCategory() }
    )

fun NewsArticle.toEntity(): NewsArticleEntity =
    NewsArticleEntity(
        title = title,
        description = description,
        imageUrl = imageUrl,
        link = link,
        author = author,
        pubDate = pubDate,
        category = category.map { it.toEntity() }
    )


fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        value = value,
    )

fun CategoryEntity.toCategory(): Category =
    Category(
        value = value,
    )

fun NewsItemDto.toNewsEntity(): NewsArticleEntity =
    NewsArticleEntity(
        title = title,
        description = description.let { stripHtml(it).shorten(300) },
        imageUrl = mediaContents.firstOrNull()?.url.orEmpty(),
        link = guid ?: link,
        author = creator.orEmpty(),
        pubDate = pubDate.orEmpty(),
        category = category.map { it.toCategoryEntity() }
    )

fun CategoryDto.toCategoryEntity(): CategoryEntity =
    CategoryEntity(
        value = value
    )
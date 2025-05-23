package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchNews(query: String): Flow<List<NewsArticle>>
}
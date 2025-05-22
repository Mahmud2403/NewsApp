package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.model.TopBar
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<List<NewsArticle>>
}
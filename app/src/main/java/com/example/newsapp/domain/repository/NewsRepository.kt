package com.example.newsapp.domain.repository

import com.example.newsapp.base.RequestResult
import com.example.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getAllCharacters(): Flow<RequestResult<List<NewsArticle>>>
}
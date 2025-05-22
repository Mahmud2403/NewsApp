package com.example.newsapp.data.repository

import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.remote.api.RssApi
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: RssApi
): NewsRepository {

    override suspend fun getNews(): Flow<List<NewsArticle>> = flow {
        emit(api.getNewsFeed().channel.items.map { it.toDomain() })
    }
}
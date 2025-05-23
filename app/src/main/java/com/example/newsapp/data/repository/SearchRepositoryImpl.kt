package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.api.RssApi
import com.example.newsapp.data.mapper.toNews
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: RssApi
): SearchRepository {

    override suspend fun searchNews(query: String): Flow<List<NewsArticle>> = flow {
        val newsList = api.getNewsFeed().body()?.channel?.items?.map { it.toNews() }
        val filtered = newsList?.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
        if (filtered != null) {
            emit(filtered)
        }
    }
}
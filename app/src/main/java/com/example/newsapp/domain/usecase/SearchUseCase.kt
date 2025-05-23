package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun invoke(query: String): Flow<List<NewsArticle>> {
        return searchRepository.searchNews(query)
    }
}
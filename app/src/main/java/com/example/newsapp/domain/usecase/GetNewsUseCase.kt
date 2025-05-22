package com.example.newsapp.domain.usecase

import com.example.newsapp.base.Result
import com.example.newsapp.base.asResult
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Flow<Result<List<NewsArticle>>> =
        repository.getNews().asResult()
}

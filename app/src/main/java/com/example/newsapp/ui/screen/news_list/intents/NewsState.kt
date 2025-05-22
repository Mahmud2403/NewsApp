package com.example.newsapp.ui.screen.news_list.intents

import com.example.newsapp.base.BaseViewState
import com.example.newsapp.domain.model.NewsArticle

sealed class NewsState: BaseViewState {
    data object Loading : NewsState()
    data class Error(val message: String?) : NewsState()
    data class Success(
        val data: List<NewsArticle> = emptyList(),
        val categories: List<String> = emptyList(),
        val selectedCategories: Set<String> = emptySet()
    ) : NewsState()
}

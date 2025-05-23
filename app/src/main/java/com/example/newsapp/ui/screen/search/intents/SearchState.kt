package com.example.newsapp.ui.screen.search.intents

import com.example.newsapp.base.BaseViewState
import com.example.newsapp.domain.model.NewsArticle

sealed class SearchState(open val searchText: String) : BaseViewState {
    class Loading(override val searchText: String) : SearchState(searchText)
    data class Success(val articles: List<NewsArticle>, override val searchText: String) : SearchState(searchText)
    data class Error(val message: String?, override val searchText: String) : SearchState(searchText)
    data class NotFound(override val searchText: String) : SearchState(searchText)
}

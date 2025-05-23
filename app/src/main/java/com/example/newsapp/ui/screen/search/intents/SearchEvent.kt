package com.example.newsapp.ui.screen.search.intents

import com.example.newsapp.base.BaseViewEvent

sealed class SearchEvent: BaseViewEvent {
    data class OnSearch(val search: String): SearchEvent()
    data class ChangeSearch(val query: String): SearchEvent()
}
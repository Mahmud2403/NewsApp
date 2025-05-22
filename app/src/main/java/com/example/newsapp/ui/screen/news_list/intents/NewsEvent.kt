package com.example.newsapp.ui.screen.news_list.intents

import com.example.newsapp.base.BaseViewEvent
import com.example.newsapp.ui.screen.search.enums.SortOption

sealed class NewsEvent: BaseViewEvent {
    object OnRefresh: NewsEvent()
    data class ToggleCategory(val category: String) : NewsEvent()
    object ApplyCategoryFilter : NewsEvent()
    object ClearCategoryFilter: NewsEvent()
    data class ApplySort(val option: SortOption) : NewsEvent()
}
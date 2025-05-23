package com.example.newsapp.ui.screen.news_list.vm

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.base.RequestResult
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.usecase.GetNewsUseCase
import com.example.newsapp.ui.screen.news_list.intents.NewsEvent
import com.example.newsapp.ui.screen.news_list.intents.NewsState
import com.example.newsapp.ui.screen.search.enums.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//
//@HiltViewModel
//class NewsViewModel @Inject constructor(
//    private val getNewsUseCase: GetNewsUseCase,
//) : BaseViewModel<NewsState, NewsEvent>(NewsState.Loading) {
//
//    private var allArticles: List<NewsArticle> = emptyList()
//    private var allCategory: List<String> = emptyList()
//
//    private var pendingSelectedCategories: MutableSet<String> = mutableSetOf()
//
//
//    init {
//        getNews()
//    }
//
//    override fun observe(event: NewsEvent) {
//        when (event) {
//            is NewsEvent.OnRefresh -> refreshNews()
//            is NewsEvent.ToggleCategory -> toggleCategory(event.category)
//            is NewsEvent.ApplyCategoryFilter -> applyCategoryFilter()
//            is NewsEvent.ClearCategoryFilter -> clearCategoryFilter()
//            is NewsEvent.ApplySort -> applySort(event.option)
//        }
//    }
//
//
//    private fun getNews() {
//        launchIOCoroutine {
//            getNewsUseCase().collect { result ->
//                when (result) {
//                    is Result.Loading -> updateState { NewsState.Loading }
//
//                    is Result.Success -> {
//                        allArticles = result.data.data ?: emptyList()
//
//                        pendingSelectedCategories = currentSelectedDCategories().toMutableSet()
//
//
//                        val allCategories = allArticles
//                            .flatMap { it.category }
//                            .map { it.value }
//                            .distinct()
//
//                        allCategory = allCategories
//
//                        updateState {
//                            NewsState.Success(
//                                data = allArticles,
//                                categories = allCategories,
//                                selectedCategories = emptySet()
//                            )
//                        }
//                    }
//
//                    is Result.Error -> updateState { NewsState.Error(result.exception?.message) }
//                }
//            }
//        }
//    }
//
//    private fun toggleCategory(category: String) {
//        if (pendingSelectedCategories.contains(category)) {
//            pendingSelectedCategories.remove(category)
//        } else {
//            pendingSelectedCategories.add(category)
//        }
//
//        val currentState = viewState.value
//        if (currentState is NewsState.Success) {
//            updateState {
//                currentState.copy(selectedCategories = pendingSelectedCategories.toSet())
//            }
//        }
//    }
//
//    private fun applyCategoryFilter() {
//        val filtered = filterArticles(allArticles, pendingSelectedCategories)
//        updateState {
//            NewsState.Success(
//                data = filtered,
//                categories = allCategory,
//                selectedCategories = pendingSelectedCategories.toSet()
//            )
//        }
//    }
//
//    private fun clearCategoryFilter() {
//        pendingSelectedCategories.clear()
//
//        val filtered = filterArticles(allArticles, emptySet())
//
//        val currentState = viewState.value
//        if (currentState is NewsState.Success) {
//            updateState {
//                currentState.copy(
//                    data = filtered,
//                    selectedCategories = emptySet()
//                )
//            }
//        }
//    }
//
//    private fun currentSelectedDCategories(): Set<String> {
//        val state = viewState.value
//        return if (state is NewsState.Success) state.selectedCategories else emptySet()
//    }
//
//    private fun filterArticles(
//        articles: List<NewsArticle>,
//        selected: Set<String>
//    ): List<NewsArticle> {
//        return if (selected.isEmpty()) {
//            articles
//        } else {
//            articles.filter { article ->
//                article.category.any { it.value in selected }
//            }
//        }
//    }
//
//    private fun applySort(option: SortOption) {
//        val articlesToSort = filterArticles(allArticles, pendingSelectedCategories)
//        val sorted = when (option) {
//            SortOption.DATE_ASC -> articlesToSort.sortedBy { it.pubDate }
//            SortOption.DATE_DESC -> articlesToSort.sortedByDescending { it.pubDate }
//            SortOption.AUTHOR_ASC -> articlesToSort.sortedBy { it.author }
//            SortOption.AUTHOR_DESC -> articlesToSort.sortedByDescending { it.author }
//        }
//
//        updateState {
//            NewsState.Success(
//                data = sorted,
//                categories = allCategory,
//                selectedCategories = pendingSelectedCategories.toSet()
//            )
//        }
//    }
//    private fun refreshNews() {
//        val currentState = viewState.value
//        if (currentState is NewsState.Success) {
//            updateState { currentState.copy(isRefreshing = true) }
//        }
//
//        launchIOCoroutine {
//            getNewsUseCase().collect { result ->
//                when (result) {
//                    is Result.Loading -> {
//                    }
//
//                    is Result.Success -> {
//                        allArticles = result.data.data ?: emptyList()
//
//                        val selected = pendingSelectedCategories.toSet()
//
//                        val allCategories = allArticles
//                            .flatMap { it.category }
//                            .map { it.value }
//                            .distinct()
//
//                        allCategory = allCategories
//
//                        val filtered = filterArticles(allArticles, selected)
//
//                        updateState {
//                            NewsState.Success(
//                                data = filtered,
//                                categories = allCategories,
//                                selectedCategories = selected,
//                                isRefreshing = false
//                            )
//                        }
//                    }
//
//                    is Result.Error -> {
//                        updateState { NewsState.Error(result.exception?.message ?: "Ошибка загрузки данных") }
//                    }
//                }
//            }
//        }
//    }
//
//}

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) : BaseViewModel<NewsState, NewsEvent>(NewsState.Loading) {

    private var allArticles: List<NewsArticle> = emptyList()
    private var allCategories: List<String> = emptyList()
    private var pendingSelectedCategories: MutableSet<String> = mutableSetOf()

    init {
        getNews()
    }

    override fun observe(event: NewsEvent) {
        when (event) {
            is NewsEvent.OnRefresh -> refreshNews()
            is NewsEvent.ToggleCategory -> toggleCategory(event.category)
            is NewsEvent.ApplyCategoryFilter -> applyCategoryFilter()
            is NewsEvent.ClearCategoryFilter -> clearCategoryFilter()
            is NewsEvent.ApplySort -> applySort(event.option)
        }
    }

    private fun getNews() {
        launchIOCoroutine {
            getNewsUseCase().collect { result ->
                when (result) {
                    is RequestResult.InProgress -> {
                        updateState { NewsState.Loading }
                    }

                    is RequestResult.Success -> {
                        val articles = result.data
                        updateWithArticles(articles, isRefreshing = false)
                    }

                    is RequestResult.Error -> {
                        updateState {
                            NewsState.Error(result.error?.message ?: "Ошибка загрузки данных")
                        }
                    }
                }
            }
        }
    }

    private fun refreshNews() {
        val currentState = viewState.value

        if (currentState is NewsState.Success) {
            updateState { currentState.copy(isRefreshing = true) }
        } else {
            updateState { NewsState.Loading }
        }

        launchIOCoroutine {
            getNewsUseCase().collect { result ->
                when (result) {
                    is RequestResult.InProgress -> {
                        updateState { NewsState.Loading }
                    }

                    is RequestResult.Success -> {
                        val articles = result.data
                        updateWithArticles(articles, isRefreshing = false)
                    }

                    is RequestResult.Error -> {
                        updateState {
                            NewsState.Error(result.error?.message ?: "Ошибка загрузки данных")
                        }
                    }
                }
            }
        }
    }


    private fun updateWithArticles(
        articles: List<NewsArticle>,
        isRefreshing: Boolean
    ) {
        updateState {
            NewsState.Success(
                data = articles,
                isRefreshing = isRefreshing
            )
        }
    }


    private fun toggleCategory(category: String) {
        pendingSelectedCategories.apply {
            if (contains(category)) remove(category) else add(category)
        }

        updateStateIfSuccess {
            it.copy(selectedCategories = pendingSelectedCategories.toSet())
        }
    }

    private fun applyCategoryFilter() {
        val filtered = filterArticles(allArticles, pendingSelectedCategories)
        updateState {
            NewsState.Success(
                data = filtered,
                categories = allCategories,
                selectedCategories = pendingSelectedCategories.toSet()
            )
        }
    }

    private fun clearCategoryFilter() {
        pendingSelectedCategories.clear()
        val filtered = filterArticles(allArticles, emptySet())

        updateStateIfSuccess {
            it.copy(
                data = filtered,
                selectedCategories = emptySet()
            )
        }
    }

    private fun applySort(option: SortOption) {
        val articlesToSort = filterArticles(allArticles, pendingSelectedCategories)
        val sorted = when (option) {
            SortOption.DATE_ASC -> articlesToSort.sortedBy { it.pubDate }
            SortOption.DATE_DESC -> articlesToSort.sortedByDescending { it.pubDate }
            SortOption.AUTHOR_ASC -> articlesToSort.sortedBy { it.author }
            SortOption.AUTHOR_DESC -> articlesToSort.sortedByDescending { it.author }
        }

        updateState {
            NewsState.Success(
                data = sorted,
                categories = allCategories,
                selectedCategories = pendingSelectedCategories.toSet()
            )
        }
    }

    private fun filterArticles(
        articles: List<NewsArticle>,
        selected: Set<String>
    ): List<NewsArticle> {
        return if (selected.isEmpty()) {
            articles
        } else {
            articles.filter { article ->
                article.category.any { it.value in selected }
            }
        }
    }

    private inline fun updateStateIfSuccess(crossinline update: (NewsState.Success) -> NewsState) {
        val current = viewState.value
        if (current is NewsState.Success) {
            updateState { update(current) }
        }
    }
}


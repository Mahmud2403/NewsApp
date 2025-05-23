package com.example.newsapp.ui.screen.search.vm

import com.example.newsapp.base.BaseViewModel
import com.example.newsapp.domain.usecase.SearchUseCase
import com.example.newsapp.ui.screen.search.intents.SearchEvent
import com.example.newsapp.ui.screen.search.intents.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : BaseViewModel<SearchState, SearchEvent>(SearchState.NotFound("")) {

    private val searchQuery = MutableStateFlow("")

    init {
        observeDebouncedSearch()
    }

    override fun observe(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearch -> {
                performSearch(event.search)
            }
            is SearchEvent.ChangeSearch -> {
                updateSearchText(event.query)
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            updateState { SearchState.NotFound(searchText = query) }
            return
        }

        launchIOCoroutine {
            searchUseCase.invoke(query)
                .catch { e ->
                    updateState { SearchState.Error(e.message ?: "Неизвестная ошибка", searchText = query) }
                }
                .collect { articles ->
                    if (articles.isEmpty()) {
                        updateState { SearchState.NotFound(searchText = query) }
                    } else {
                        updateState { SearchState.Success(articles, searchText = query) }
                    }
                }
        }
    }

    private fun observeDebouncedSearch() {
        launchCoroutine {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    performSearch(query)
                }
        }
    }

    private fun updateSearchText(newValue: String) {
        if (searchQuery.value == newValue) return

        searchQuery.value = newValue
        updateState {
            when (this) {
                is SearchState.Success -> copy(searchText = newValue)
                is SearchState.Error -> copy(searchText = newValue)
                is SearchState.NotFound -> copy(searchText = newValue)
                is SearchState.Loading -> SearchState.Loading(searchText = newValue)
            }
        }
    }
}

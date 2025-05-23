package com.example.newsapp.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.common.EmptyList
import com.example.newsapp.common.ErrorMessage
import com.example.newsapp.common.SearchTopBar
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.ui.screen.news_list.components.NewsCard
import com.example.newsapp.ui.screen.search.intents.SearchEvent
import com.example.newsapp.ui.screen.search.intents.SearchState
import com.example.newsapp.ui.screen.search.vm.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickNews: (String) -> Unit,
    viewModel: SearchViewModel,
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            SearchTopBar(
                searchText = uiState.searchText,
                onValueChange = {
                    viewModel.perform(SearchEvent.ChangeSearch(it))
                },
                onClickBack = onClickBack,
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        when (val currentState = uiState) {
            is SearchState.NotFound -> SearchNotFound(
                modifier = modifier
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
            )

            is SearchState.Loading -> SearchLoading(
                modifier = modifier
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
            )

            is SearchState.Success -> SearchSuccess(
                modifier = modifier
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    ),
                data = currentState.articles,
                onClickNews = onClickNews,
                searchText = uiState.searchText,
            )

            is SearchState.Error -> SearchError(
                modifier = modifier
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    ),
                errorText = currentState.message ?: "Unknown Error"
            )
        }
    }
}

@Composable
private fun SearchSuccess(
    modifier: Modifier = Modifier,
    data: List<NewsArticle>,
    onClickNews: (String) -> Unit,
    searchText: String,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(
            items = data
        ) { data ->
            NewsCard(
                data = data,
                searchText = searchText,
                onClickNews = {
                    onClickNews(data.link)
                }
            )
        }
    }
}

@Composable
private fun SearchNotFound(
    modifier: Modifier = Modifier,
) {
    EmptyList(
        modifier = modifier,
        value = "Nothing was found :("
    )
}

@Composable
private fun SearchLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun SearchError(
    modifier: Modifier = Modifier,
    errorText: String,
) {
    ErrorMessage(
        modifier = modifier,
        text = errorText,
    )
}

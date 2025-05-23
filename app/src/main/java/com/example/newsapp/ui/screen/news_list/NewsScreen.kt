package com.example.newsapp.ui.screen.news_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.common.CategoryFilterBottomSheet
import com.example.newsapp.common.EmptyList
import com.example.newsapp.common.NewsTopBar
import com.example.newsapp.common.SortDropdownMenu
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.ui.screen.news_list.components.LoadingNewsCard
import com.example.newsapp.ui.screen.news_list.components.NewsCard
import com.example.newsapp.ui.screen.news_list.intents.NewsEvent
import com.example.newsapp.ui.screen.news_list.intents.NewsState
import com.example.newsapp.ui.screen.news_list.vm.NewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
    onClickNews: (String) -> Unit,
    onClickSearch: () -> Unit,
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var isSheetOpen by remember { mutableStateOf(false) }
    var isSortMenuOpen by remember { mutableStateOf(false) }

    val refreshState = rememberPullRefreshState(
        refreshing = (uiState as? NewsState.Success)?.isRefreshing == true,
        onRefresh = {
            viewModel.perform(NewsEvent.OnRefresh)
        }
    )


    if (isSheetOpen && uiState is NewsState.Success) {
        val successState = uiState as NewsState.Success
        CategoryFilterBottomSheet(
            sheetState = sheetState,
            categories = successState.categories,
            selectedCategories = successState.selectedCategories,
            onToggleCategory = { category ->
                viewModel.perform(NewsEvent.ToggleCategory(category))
            },
            onApply = {
                viewModel.perform(NewsEvent.ApplyCategoryFilter)
                coroutineScope.launch { sheetState.hide() }
                isSheetOpen = false
            },
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }
                isSheetOpen = false
            },
            onClickClear = {
                viewModel.perform(NewsEvent.ClearCategoryFilter)
            }
        )
    }


    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            Box(
                modifier = Modifier
            ) {
                NewsTopBar(
                    title = "The Guardian",
                    onClickSearch = onClickSearch,
                    onClickFilter = { isSheetOpen = true },
                    onClickSort = { isSortMenuOpen = true },
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 56.dp, end = 8.dp)
                ) {
                    SortDropdownMenu(
                        expanded = isSortMenuOpen,
                        onDismiss = { isSortMenuOpen = false },
                        onSortSelected = { selectedOption ->
                            viewModel.perform(NewsEvent.ApplySort(selectedOption))
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
        ) {
            PullRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(2f)
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    ),
                refreshing = (uiState as? NewsState.Success)?.isRefreshing == true,
                state = refreshState,
            )
            when (val currentState = uiState) {
                is NewsState.Error -> NewsError(
                    modifier = modifier
                        .padding(
                            top = innerPadding.calculateTopPadding()
                        ),
                    errorMessage = currentState.message,
                    onRefresh = {
                        viewModel.perform(NewsEvent.OnRefresh)
                    }
                )

                NewsState.Loading -> LoadingNews(
                    modifier = modifier
                        .padding(
                            top = innerPadding.calculateTopPadding()
                        )
                )

                is NewsState.Success ->
                    if (currentState.data.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            EmptyList(
                                value = "Empty news"
                            )
                        }
                    } else {
                        NewsList(
                            modifier = modifier
                                .padding(
                                    top = innerPadding.calculateTopPadding()
                                )
                                .fillMaxSize(),
                            data = currentState.data,
                            onClickNews = onClickNews
                        )
                    }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun NewsList(
    modifier: Modifier = Modifier,
    data: List<NewsArticle>,
    onClickNews: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(
            items = data,
        ) { news ->
            NewsCard(
                data = news,
                onClickNews = {
                    onClickNews(news.link)
                }
            )
        }
    }
}


@Composable
private fun LoadingNews(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(itemCount) {
            LoadingNewsCard()
        }
    }
}

@Composable
private fun NewsError(
    modifier: Modifier = Modifier,
    errorMessage: String?,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = errorMessage ?: "Unknown error",
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Row(
            modifier = Modifier
                .clickable(onClick = onRefresh),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Обновить"
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
        }
    }
}

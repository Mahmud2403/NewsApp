package com.example.newsapp.ui.screen.news_list.navigation

import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.newsapp.navigation.RootNavigation
import com.example.newsapp.ui.screen.news_detail.navigation.NewsDetailNavigation
import com.example.newsapp.ui.screen.news_list.NewsScreen
import com.example.newsapp.ui.screen.news_list.vm.NewsViewModel
import com.example.newsapp.ui.screen.search.navigation.SearchNavigation

object NewsNavigation: RootNavigation {
    override val route: String = "news_navigation"
}

fun NavGraphBuilder.news(
    navigateTo: (route: String) -> Unit
) {
    composable(
        route = NewsNavigation.route
    ) {
        val viewModel = hiltViewModel<NewsViewModel>()

        NewsScreen(
            viewModel = viewModel,
            onClickNews = { url ->
                val encodedUrl = Uri.encode(url)
                navigateTo(
                    NewsDetailNavigation.navigateWithArgument(encodedUrl)
                )
            },
            onClickSearch = {
                navigateTo(
                    SearchNavigation.route
                )
            }
        )
    }
}
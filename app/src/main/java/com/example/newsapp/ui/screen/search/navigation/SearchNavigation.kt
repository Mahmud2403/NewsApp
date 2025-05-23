package com.example.newsapp.ui.screen.search.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.newsapp.navigation.RootNavigation
import com.example.newsapp.ui.screen.news_detail.navigation.NewsDetailNavigation
import com.example.newsapp.ui.screen.news_list.navigation.NewsNavigation
import com.example.newsapp.ui.screen.search.SearchScreen
import com.example.newsapp.ui.screen.search.vm.SearchViewModel

object SearchNavigation : RootNavigation {
    override val route: String = "search_route"
}

fun NavGraphBuilder.search(
    navigateTo: (route: String) -> Unit,
    onClickBack: () -> Unit,
    navHostController: NavHostController
) {
    composable(
        route = SearchNavigation.route
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navHostController.getBackStackEntry(NewsNavigation.route)
        }

        val viewModel = hiltViewModel<SearchViewModel>(parentEntry)

        SearchScreen(
            onClickBack = onClickBack,
            onClickNews = { url ->
                navigateTo(
                    NewsDetailNavigation.navigateWithArgument(url)
                )
            },
            viewModel = viewModel,
        )
    }
}
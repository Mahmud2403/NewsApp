package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.newsapp.ui.screen.news_detail.navigation.newsDetail
import com.example.newsapp.ui.screen.news_list.navigation.NewsNavigation
import com.example.newsapp.ui.screen.news_list.navigation.news
import com.example.newsapp.ui.screen.search.navigation.search


@Composable
fun NewsNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onNavigation: (route: String) -> Unit,
    onClickBack: () -> Unit,
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NewsNavigation.route
    ) {
        news(
            navigateTo = onNavigation
        )
        newsDetail(
            onBackClick = onClickBack
        )
        search(
            navigateTo = onNavigation,
            onClickBack = onClickBack,
            navHostController = navHostController
        )
    }
}
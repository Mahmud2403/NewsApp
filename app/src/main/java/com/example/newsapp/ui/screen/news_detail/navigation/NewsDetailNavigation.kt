package com.example.newsapp.ui.screen.news_detail.navigation

import android.net.Uri
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.navigation.RootNavigation
import com.example.newsapp.ui.screen.news_detail.NewsDetailScreen
import com.example.newsapp.ui.screen.news_list.navigation.NewsNavigation

object NewsDetailNavigation : RootNavigation {
    override val route: String = "news_detail/{url}"

    fun navigateWithArgument(url: String) =
        "news_detail/$url"
}

fun NavGraphBuilder.newsDetail(
    onBackClick: () -> Unit,
) {
    composable(
        route = NewsDetailNavigation.route,
        arguments = listOf(
            navArgument("url") { type = NavType.StringType}
        )
    ) { backStackEntry ->

        val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")

        NewsDetailScreen(
            url = url,
            onClickBack = onBackClick
        )
    }
}

package com.example.newsapp.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    appState: AppState,
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding(),
        containerColor = Color.White,
    ) {
        NewsNavHost(
            navHostController = appState.navController,
            onNavigation = appState::navigate,
            onClickBack = appState::onBackClick,
        )
    }
}


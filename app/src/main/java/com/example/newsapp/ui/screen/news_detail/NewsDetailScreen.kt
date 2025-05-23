package com.example.newsapp.ui.screen.news_detail

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.newsapp.common.NewsWebView
import com.example.newsapp.ui.theme.Grey200
import com.example.newsapp.ui.theme.Grey300

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    url: String,
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        if (webView.canGoBack()) {
                            webView.goBack()
                        } else {
                            onClickBack()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    ) { innerPadding ->
        NewsWebView(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding()
                ),
            url = url,
        )
    }
}
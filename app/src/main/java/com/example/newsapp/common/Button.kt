package com.example.newsapp.common

import android.content.Intent
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.rounded.Filter
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@androidx.compose.runtime.Composable
fun ShareIcon(
    modifier: Modifier = Modifier,
    url: String,
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    IconButton (
        modifier = modifier
            .size(16.dp).indication(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 18.dp,
                    color = Color.Transparent
                )
            ),
        onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
            }
            context.startActivity(
                Intent.createChooser(shareIntent, "Поделиться ссылкой")
            )
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Share,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}


@androidx.compose.runtime.Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    onClickSearch: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    IconButton (
        modifier = modifier
            .size(30.dp)
            .indication(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 18.dp,
                    color = Color.Transparent
                )
            ),
        onClick = onClickSearch,
        interactionSource = interactionSource,
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@androidx.compose.runtime.Composable
fun FilterIcon(
    modifier: Modifier = Modifier,
    onClickFilter: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    IconButton(
        modifier = modifier
            .size(30.dp)
            .indication(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 18.dp,
                    color = Color.Transparent
                )
            ),
        onClick = onClickFilter
    ) {
        Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@androidx.compose.runtime.Composable
fun SortIcon(
    modifier: Modifier = Modifier,
    onClickSort: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    IconButton (
        modifier = modifier
            .size(30.dp)
            .indication(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 18.dp,
                    color = Color.Transparent
                )
            ),
        onClick = onClickSort
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.Sort,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}
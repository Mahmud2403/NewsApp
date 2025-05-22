package com.example.newsapp.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R

@Composable
fun AdjustableImage(
    modifier: Modifier = Modifier,
    url: String
) {
    AsyncImage(
        modifier = modifier
            .size(
                width = 140.dp,
                height = 180.dp
            )
            .clip(RoundedCornerShape(10.dp)),
        model = url,
//        placeholder = painterResource(R.drawable.placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}
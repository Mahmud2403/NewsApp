package com.example.newsapp.ui.screen.news_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.common.AdjustableImage
import com.example.newsapp.common.Author
import com.example.newsapp.common.Description
import com.example.newsapp.common.PubDate
import com.example.newsapp.common.ShareIcon
import com.example.newsapp.common.Tag
import com.example.newsapp.common.Title
import com.example.newsapp.common.highlightText
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.util.formatPubDate

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    data: NewsArticle,
    searchText: String = "",
    onClickNews: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(
                onClick = onClickNews
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AdjustableImage(
                url = data.imageUrl
            )
            Column(
                modifier = Modifier
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Tag(
                        text = data.category[0].value,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Author(
                        text = data.author,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
                Title(
                    text = highlightText(searchText, data.title),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Description(
                    text = highlightText(searchText, data.description),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    PubDate(
                        text = formatPubDate(data.pubDate),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    ShareIcon(
                        url = data.link
                    )
                }
            }
        }
    }
}
package com.example.newsapp.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight(200),
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
    )
}

@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight(600),
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = 2,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines
    )
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight(400),
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    maxLines: Int = 2,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
    )
}

@Composable
fun PubDate(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 8.sp,
    fontWeight: FontWeight = FontWeight(200),
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
    )
}

@Composable
fun Author(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight(200),
    color: Color = MaterialTheme.colorScheme.tertiary,
    maxLines: Int = 1,
    maxWidth: Int = 150,
) {
    Text(
        modifier = modifier
            .width(maxWidth.dp),
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        textAlign = TextAlign.End
    )
}


@Composable
fun EmptyList(
    modifier: Modifier = Modifier,
    value: String
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.Red,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
fun highlightText(searchText: String, fullText: String): AnnotatedString {
    val startIndex = fullText.indexOf(searchText, ignoreCase = true)
    return if (startIndex != -1) {
        val endIndex = startIndex + searchText.length
        buildAnnotatedString {
            append(fullText.substring(0, startIndex))
            withStyle(style = SpanStyle(color = Color.Magenta)) {
                append(fullText.substring(startIndex, endIndex))
            }
            append(fullText.substring(endIndex, fullText.length))
        }
    } else {
        AnnotatedString(fullText)
    }
}
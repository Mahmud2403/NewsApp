package com.example.newsapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickableWithRipple(
    radius: Dp = 18.dp,
    color: Color = Color.Unspecified,
    enabled: Boolean = true,
    bounded: Boolean = false,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }

    this
        .indication(
            interactionSource = interactionSource,
            indication = ripple(
                bounded = bounded,
                radius = radius,
                color = color
            )
        )
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = enabled,
            onClick = onClick
        )
}

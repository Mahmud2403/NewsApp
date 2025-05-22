package com.example.newsapp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.createRippleModifierNode
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.util.clickableWithRipple

@Composable
fun NewsTopBar(
    modifier: Modifier = Modifier,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit,
    onClickSort: () -> Unit,
    title: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp)),
            painter = painterResource(R.drawable.ic_apple_touch_icon),
            contentDescription = null,
        )
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 23.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        SearchIcon(
            modifier = Modifier,
            onClickSearch = onClickSearch
        )
        FilterIcon(
            onClickFilter = onClickFilter
        )
        SortIcon(
            onClickSort = onClickSort
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchText: String,
    onValueChange: (String) -> Unit,
    onClickBack: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Surface(
        shadowElevation = 10.dp,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 20.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickableWithRipple(
                        onClick = onClickBack
                    ),
                tint = MaterialTheme.colorScheme.secondary,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )

            BasicTextField(
                value = searchText,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                interactionSource = remember { MutableInteractionSource() },
                enabled = true,
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
            ) { innerTextField ->

                TextFieldDefaults.DecorationBox(
                    value = searchText,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = "Search news",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    contentPadding = PaddingValues(0.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }
            if (searchText.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                onValueChange("")
                                focusManager.clearFocus()
                                focusRequester.requestFocus()
                            },
                            indication = null,
                        ),
                    imageVector = Icons.Rounded.Cancel,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }
        }
    }
}
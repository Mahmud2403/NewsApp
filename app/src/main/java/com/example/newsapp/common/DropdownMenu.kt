package com.example.newsapp.common

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.newsapp.ui.screen.search.enums.SortOption

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSortSelected: (SortOption) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        SortOption.entries.forEach { option ->
            DropdownMenuItem(
                text = { Text(option.label) },
                onClick = {
                    onDismiss()
                    onSortSelected(option)
                }
            )
        }
    }
}

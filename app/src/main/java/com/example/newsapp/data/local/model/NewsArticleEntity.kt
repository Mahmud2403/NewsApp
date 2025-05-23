package com.example.newsapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val author: String,
    val pubDate: String,
    @ColumnInfo(name = "categories") val category: List<CategoryEntity>,
)

data class CategoryEntity(
    val domain: String? = null,
    val value: String = ""
)

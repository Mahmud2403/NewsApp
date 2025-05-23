package com.example.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.model.Converters
import com.example.newsapp.data.local.model.NewsArticleEntity

@Database(
    entities = [
        NewsArticleEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract val dao: NewsDao
}
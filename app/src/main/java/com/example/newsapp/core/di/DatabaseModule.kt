package com.example.newsapp.core.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.dao.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): NewsDatabase {
        return Room.databaseBuilder(app, NewsDatabase::class.java, "news_db").build()
    }

    @Singleton
    @Provides
    fun provideMassDao(db: NewsDatabase): NewsDao = db.dao
}
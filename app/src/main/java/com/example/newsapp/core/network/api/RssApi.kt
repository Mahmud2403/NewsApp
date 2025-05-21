package com.example.newsapp.core.network.api

import com.example.newsapp.core.network.model.NewsDto
import retrofit2.Response
import retrofit2.http.GET

interface RssApi {
    @GET("international/rss")
    suspend fun getNewsFeed(): NewsDto
}
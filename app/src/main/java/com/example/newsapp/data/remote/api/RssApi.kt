package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.model.NewsDto
import retrofit2.Response
import retrofit2.http.GET

interface RssApi {
    @GET("international/rss")
    suspend fun getNewsFeed(): Response<NewsDto>
}
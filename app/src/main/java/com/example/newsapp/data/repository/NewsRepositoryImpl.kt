package com.example.newsapp.data.repository

import android.util.Log
import com.example.newsapp.base.RequestResponseMergeStrategy
import com.example.newsapp.base.RequestResult
import com.example.newsapp.base.map
import com.example.newsapp.base.toRequestResult
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.model.NewsArticleEntity
import com.example.newsapp.data.mapper.toNews
import com.example.newsapp.data.mapper.toNewsEntity
import com.example.newsapp.data.remote.api.RssApi
import com.example.newsapp.data.remote.model.NewsDto
import com.example.newsapp.domain.model.NewsArticle
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: RssApi,
    private val dao: NewsDao,
) : NewsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllNews(): Flow<RequestResult<List<NewsArticle>>> {
        val cacheFlow = getAllFromCache()
        val networkFlow = getAllFromNetwork()
        val mergeStrategy = RequestResponseMergeStrategy<List<NewsArticle>>()

        return cacheFlow.combine(networkFlow, mergeStrategy::merge)
            .flatMapLatest { result ->
                when (result) {
                    is RequestResult.Success -> {
                        if (result.data.isEmpty()) {
                            flowOf(RequestResult.Success(emptyList()))
                        } else {
                            dao.observeNews()
                                .map { newsList ->
                                    val articles = newsList.map { it.toNews() }
                                    RequestResult.Success(articles)
                                }
                        }
                    }

                    is RequestResult.Error -> {
                        flowOf(RequestResult.Error(error = result.error))
                    }

                    is RequestResult.InProgress -> {
                        flowOf(RequestResult.InProgress())
                    }
                }
            }
    }


    private fun getAllFromNetwork(): Flow<RequestResult<List<NewsArticle>>> {
        val request = flow { emit(api.getNewsFeed()) }
            .onEach { result ->
                if (result.isSuccessful) result.body()?.let {
                    saveNewsToCache(it) }
            }
            .map { it.toRequestResult() }
            .catch { e ->
                emit(RequestResult.Error(error = e))  }

        val start = flowOf<RequestResult<NewsDto>>(RequestResult.InProgress())
        return merge(request, start)
            .map { result: RequestResult<NewsDto> ->
                result.map { entity ->
                    entity.channel.items.map {
                        it.toNews()
                    }
                }
            }
    }

    private suspend fun saveNewsToCache(data: NewsDto) {
        val entity = data.channel.items.map { it.toNewsEntity() }
        dao.insertNews(entity)
    }

    private fun getAllFromCache(): Flow<RequestResult<List<NewsArticle>>> {
        val cached = flow {
            val news = dao.getNews()
            emit(news)
        }
            .map<List<NewsArticleEntity>, RequestResult<List<NewsArticle>>> { list ->
                RequestResult.Success(list.map { it.toNews() })
            }
            .catch { error ->
                emit(RequestResult.Error(error = error))
            }

        val start = flowOf<RequestResult<List<NewsArticle>>>(RequestResult.InProgress())

        return merge(start, cached)
    }
}
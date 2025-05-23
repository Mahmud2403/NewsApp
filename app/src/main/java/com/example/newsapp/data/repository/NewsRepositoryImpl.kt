package com.example.newsapp.data.repository

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
    override suspend fun getAllCharacters(): Flow<RequestResult<List<NewsArticle>>> {
        val mergeStrategy = RequestResponseMergeStrategy<List<NewsArticle>>()
        val cache: Flow<RequestResult<List<NewsArticle>>> = getAllFromCache()
        val network: Flow<RequestResult<List<NewsArticle>>> = getAllFromNetwork()

        return cache.combine(network, mergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is RequestResult.Success) {
                    dao.observeCharacter()
                        .map { news ->
                            news.map {
                                it.toNews()
                            }
                        }
                        .map { RequestResult.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    private fun getAllFromNetwork(): Flow<RequestResult<List<NewsArticle>>> {
        val request = flow { emit(api.getNewsFeed()) }
            .onEach { result ->
                if (result.isSuccessful) result.body()?.let { saveNewsToCache(it) }
            }
            .map { it.toRequestResult() }

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

    private fun saveNewsToCache(data: NewsDto) {
        val entity = data.channel.items.map { it.toNewsEntity() }
        dao.insertNews(entity)
    }

    private suspend fun getAllFromCache(): Flow<RequestResult<List<NewsArticle>>> {
        val request = dao.getCharacter()

        val end = if (request != null) {
            flowOf<RequestResult<List<NewsArticleEntity>>>(RequestResult.Success(request))
        } else {
            flowOf<RequestResult<List<NewsArticleEntity>>>(RequestResult.Error(error = Throwable("Cache is empty")))
        }
        val start = flowOf<RequestResult<List<NewsArticleEntity>>>(RequestResult.InProgress())

        return merge(start, end).map { newsList ->
            newsList.map { result ->
                result.map {
                    it.toNews()
                }
            }
        }
    }
}
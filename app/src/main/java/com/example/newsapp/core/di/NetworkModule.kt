package com.example.newsapp.core.di

import com.example.newsapp.core.network.api.RssApi
import com.example.newsapp.core.util.XmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.XmlSerializationPolicy
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(ExperimentalXmlUtilApi::class)
    @Provides
    fun provideXml(): XML = XML {
        autoPolymorphic = true
        indentString = "  "
        isCollectingNSAttributes = true
//        policy = XmlSerializationPolicy()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.theguardian.com/")
            .addConverterFactory(XmlConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRssApi(retrofit: Retrofit): RssApi {
        return retrofit.create(RssApi::class.java)
    }
}
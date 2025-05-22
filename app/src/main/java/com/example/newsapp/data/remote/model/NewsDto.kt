package com.example.newsapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.*

    @Serializable
    @XmlSerialName("rss", namespace = "", prefix = "")
    data class NewsDto(
        @XmlElement(true)
        @XmlSerialName("channel", namespace = "")
        val channel: ChannelDto,

        @XmlSerialName("version")
        val version: String = "2.0"
    )

    @Serializable
    @XmlSerialName("channel", namespace = "", prefix = "")
    data class ChannelDto(
        @XmlElement(true)
        val title: String,

        @XmlElement(true)
        val link: String,

        @XmlElement(true)
        val description: String,

        @XmlElement(true)
        val language: String? = null,

        @XmlElement(true)
        val copyright: String? = null,

        @XmlElement(true)
        val pubDate: String? = null,

        @XmlElement(true)
        @XmlSerialName("date", namespace = "http://purl.org/dc/elements/1.1/", prefix = "dc")
        val dcDate: String? = null,

        @XmlElement(true)
        @XmlSerialName("language", namespace = "http://purl.org/dc/elements/1.1/", prefix = "dc")
        val dcLanguage: String? = null,

        @XmlElement(true)
        @XmlSerialName("rights", namespace = "http://purl.org/dc/elements/1.1/", prefix = "dc")
        val dcRights: String? = null,

        @XmlElement(true)
        val image: ImageDto,

        @XmlElement(true)
        @XmlSerialName("item", namespace = "", prefix = "")
        val items: List<NewsItemDto> = emptyList()
    )

    @Serializable
    @XmlSerialName("item", "", "")
    data class NewsItemDto(
        @XmlElement(true)
        val title: String,

        @XmlElement(true)
        val link: String,

        @XmlElement(true)
        val description: String,

        @XmlElement(true)
        val pubDate: String? = null,

        @XmlElement(true)
        val guid: String? = null,

        @XmlElement(true)
        val category: List<CategoryDto> = emptyList(),

        @XmlElement(true)
        @XmlSerialName("creator", "http://purl.org/dc/elements/1.1/", "dc")
        val creator: String? = null,

        @XmlElement(true)
        @XmlSerialName("date", "http://purl.org/dc/elements/1.1/", "dc")
        val dcDate: String? = null,

        @XmlElement(true)
        @XmlSerialName("content", "http://search.yahoo.com/mrss/", "media")
        val mediaContents: List<MediaContentDto> = emptyList()
    )


    @Serializable
    @XmlSerialName("image", namespace = "", prefix = "")
    data class ImageDto(
        @XmlElement(true)
        val title: String,

        @XmlElement(true)
        val url: String,

        @XmlElement(true)
        val link: String
    )

    @Serializable
    @XmlSerialName("category", "", "")
    data class CategoryDto(
        val domain: String? = null,

        @XmlValue(true)
        val value: String = ""
    )


    @Serializable
    @XmlSerialName("content", "http://search.yahoo.com/mrss/", "media")
    data class MediaContentDto(
        val url: String,

        val width: Int? = null,

        @XmlElement(true)
        val credit: MediaCreditDto? = null
    )

    @Serializable
    @XmlSerialName("credit", "http://search.yahoo.com/mrss/", "media")
    data class MediaCreditDto(
        val scheme: String? = null,

        @XmlValue(true)
        val value: String
    )


package com.example.newsapp.core.util
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class XmlConverterFactory private constructor(
    private val xml: XML
) : Converter.Factory() {

    companion object {
        fun create(xml: XML = XML()): XmlConverterFactory = XmlConverterFactory(xml)

        private val MEDIA_TYPE: MediaType = "application/xml; charset=utf-8".toMediaType()
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val loader = xml.serializersModule.serializer(type)
        return XmlResponseBodyConverter(xml, loader)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val serializer = xml.serializersModule.serializer(type)
        return XmlRequestBodyConverter(xml, serializer)
    }

    private class XmlRequestBodyConverter<T>(
        private val xml: XML,
        private val serializer: KSerializer<T>
    ) : Converter<T, RequestBody> {
        override fun convert(value: T): RequestBody {
            val xmlString = xml.encodeToString(serializer, value)
            return RequestBody.create(MEDIA_TYPE, xmlString)
        }
    }

    private class XmlResponseBodyConverter<T>(
        private val xml: XML,
        private val deserializer: KSerializer<T>
    ) : Converter<ResponseBody, T> {
        override fun convert(value: ResponseBody): T {
            val xmlString = value.string()
            return xml.decodeFromString(deserializer, xmlString)
        }
    }
}

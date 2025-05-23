package com.example.newsapp.data.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCategoryEntity(info: List<CategoryEntity>): String {
        return gson.toJson(info)
    }

    @TypeConverter
    fun toCategoryEntity(json: String): List<CategoryEntity> {
        val type = object : TypeToken<List<CategoryEntity>>() {}.type
        return gson.fromJson(json, type)
    }
}
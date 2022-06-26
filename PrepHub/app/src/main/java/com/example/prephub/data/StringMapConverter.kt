package com.example.prephub.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringMapConverter {

    @TypeConverter
    fun fromString(value: String): Map<String, String> {
        val mapType = object: TypeToken<@JvmSuppressWildcards Map<String, String>>(){}.type

        return Gson().fromJson(
            value,
            mapType
        )
    }

    @TypeConverter
    fun toString(value: Map<String, String>): String {
        return Gson().toJson(value)
    }
}
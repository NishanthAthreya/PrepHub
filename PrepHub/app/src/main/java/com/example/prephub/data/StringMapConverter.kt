package com.example.prephub.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StringMapConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): @JvmSuppressWildcards Map<@JvmSuppressWildcards String,@JvmSuppressWildcards String> {
        val mapType = object: TypeToken<@JvmSuppressWildcards Map<String, String>>(){}.type

        return Gson().fromJson(
            value,
            mapType
        )
    }

    @TypeConverter
    @JvmStatic
    fun toString(value: @JvmSuppressWildcards Map<@JvmSuppressWildcards String,@JvmSuppressWildcards String>): String {
        return Gson().toJson(value)
    }
}
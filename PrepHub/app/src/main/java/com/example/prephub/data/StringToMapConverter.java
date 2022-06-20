package com.example.prephub.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * String to map converter.
 */
public class StringToMapConverter {

    @TypeConverter
    public static Map<String,String> fromJson(String value) {
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();

        return new Gson().fromJson(
                value,
                mapType
        );
    }

    @TypeConverter
    public static String toJson(Map<String, String> value) {
        return new Gson().toJson(value);
    }
}

package com.example.thrivematch.data.roomdb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converts {

    @TypeConverter
    fun fromString(value: String): MutableList<String>? {
        val listType = object : TypeToken<MutableList<String>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String>?): String {
        return Gson().toJson(list)
    }
}
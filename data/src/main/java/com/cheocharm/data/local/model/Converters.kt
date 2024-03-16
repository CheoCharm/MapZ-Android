package com.cheocharm.data.local.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(private val gson: Gson) {
    @TypeConverter
    fun fromMembers(value: List<GroupMember>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToMember(string: String?): List<GroupMember>? {
        val collectionType = object : TypeToken<List<GroupMember>>() {}.type
        return gson.fromJson(string, collectionType)
    }
}

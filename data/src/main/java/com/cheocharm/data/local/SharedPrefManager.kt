package com.cheocharm.data.local

interface SharedPrefManager {

    fun setString(key: String, value: String)

    fun getString(key: String): String?

    fun setBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean

    fun remove(key: String)
}

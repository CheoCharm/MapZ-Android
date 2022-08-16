package com.cheocharm.local

interface SharedPrefManager {

    fun setString(key: String, value: String)

    fun getString(key: String): String?
}

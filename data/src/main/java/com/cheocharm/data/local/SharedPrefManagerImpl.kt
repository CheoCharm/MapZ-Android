package com.cheocharm.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SharedPrefManager {

    private val prefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun setBoolean(key: String, value: Boolean) {
        prefs.edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, true)
    }

    override fun remove(key: String) {
        prefs.edit()
            .remove(key)
            .apply()
    }

    companion object {
        private const val FILE_NAME = "com.cheocharm.mapz.sharedpreference"
    }
}

package com.cheocharm.local

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

    companion object {
        private const val FILE_NAME = "token"
    }
}

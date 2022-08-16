package com.cheocharm.local.source

import com.cheocharm.data.source.AuthLocalDataSource
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.local.SharedPrefManager
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : AuthLocalDataSource {

    override fun saveTokens(mapZSign: MapZSign) {
        sharedPrefManager.setString(ACCESS_TOKEN, mapZSign.accessToken)
        sharedPrefManager.setString(REFRESH_TOKEN, mapZSign.refreshToken)
    }

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
    }
}

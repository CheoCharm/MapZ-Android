package com.cheocharm.data.local.source

import com.cheocharm.data.local.SharedPrefManager
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPrefManager: SharedPrefManager
) : AuthLocalDataSource {

    override fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPrefManager.setString(ACCESS_TOKEN, accessToken)
        sharedPrefManager.setString(REFRESH_TOKEN, refreshToken)
    }

    override fun saveIsAutoSignIn(isAutoSignIn: Boolean) {
        sharedPrefManager.setBoolean(IS_AUTO_SIGN_IN, isAutoSignIn)
    }

    override fun fetchAccessToken(): String? {
        return sharedPrefManager.getString(ACCESS_TOKEN)
    }

    override fun fetchRefreshToken(): String? {
        return sharedPrefManager.getString(REFRESH_TOKEN)
    }

    override fun fetchIsAutoSignIn(): Boolean {
        return sharedPrefManager.getBoolean(IS_AUTO_SIGN_IN)
    }

    override fun removeAccessToken() {
        sharedPrefManager.remove(ACCESS_TOKEN)
    }

    override fun removeRefreshToken() {
        sharedPrefManager.remove(REFRESH_TOKEN)
    }

    override fun removeIsAutoSignIn() {
        sharedPrefManager.remove(IS_AUTO_SIGN_IN)
    }

    override fun saveSignInType(signInType: String) {
        sharedPrefManager.setString(SIGN_IN_TYPE, signInType)
    }

    override fun removeSignInType() {
        sharedPrefManager.remove(SIGN_IN_TYPE)
    }

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
        private const val IS_AUTO_SIGN_IN = "isAutoSignIn"
        private const val SIGN_IN_TYPE = "signInType"
    }
}

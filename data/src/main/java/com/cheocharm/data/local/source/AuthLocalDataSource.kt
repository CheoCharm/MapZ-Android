package com.cheocharm.data.local.source

interface AuthLocalDataSource {

    fun saveTokens(accessToken: String, refreshToken: String)

    fun saveIsAutoSignIn(isAutoSignIn: Boolean)

    fun fetchAccessToken(): String?

    fun fetchRefreshToken(): String?

    fun fetchIsAutoSignIn(): Boolean

    fun removeAccessToken()

    fun removeRefreshToken()

    fun removeIsAutoSignIn()

    fun saveSignInType(signInType: String)

    fun removeSignInType()
}

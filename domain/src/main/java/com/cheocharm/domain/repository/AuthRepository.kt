package com.cheocharm.domain.repository

interface AuthRepository {

    fun saveTokens(accessToken: String, refreshToken: String)

    fun saveIsAutoSignIn(isAutoSignIn: Boolean)

    fun fetchAccessToken(): String?

    fun fetchRefreshToken(): String?

    fun fetchIsAutoSignIn(): Boolean

    fun removeAccessToken()

    fun removeRefreshToken()

    fun removeIsAutoSignIn()
}

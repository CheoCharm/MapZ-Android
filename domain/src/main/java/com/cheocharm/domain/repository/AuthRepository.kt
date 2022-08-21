package com.cheocharm.domain.repository

interface AuthRepository {

    suspend fun refreshAccessToken(refreshToken: String): Result<String>

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

package com.cheocharm.domain.repository

import com.cheocharm.domain.model.Token

interface AuthRepository {

    suspend fun refreshAccessToken(refreshToken: String): Result<Token>

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

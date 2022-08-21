package com.cheocharm.data.repository

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.source.AuthLocalDataSource
import com.cheocharm.data.source.AuthRemoteDataSource
import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun refreshAccessToken(refreshToken: String): Result<String> {
        val result = authRemoteDataSource.refreshAccessToken(refreshToken)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        authLocalDataSource.saveTokens(accessToken, refreshToken)
    }

    override fun saveIsAutoSignIn(isAutoSignIn: Boolean) {
        authLocalDataSource.saveIsAutoSignIn(isAutoSignIn)
    }

    override fun fetchAccessToken(): String? {
        return authLocalDataSource.fetchAccessToken()
    }

    override fun fetchRefreshToken(): String? {
        return authLocalDataSource.fetchRefreshToken()
    }

    override fun fetchIsAutoSignIn(): Boolean {
        return authLocalDataSource.fetchIsAutoSignIn()
    }

    override fun removeAccessToken() {
        authLocalDataSource.removeAccessToken()
    }

    override fun removeRefreshToken() {
        authLocalDataSource.removeRefreshToken()
    }

    override fun removeIsAutoSignIn() {
        authLocalDataSource.removeIsAutoSignIn()
    }

    override fun saveSignInType(signInType: String) {
        authLocalDataSource.saveSignInType(signInType)
    }

    override fun removeSignInType() {
        authLocalDataSource.removeSignInType()
    }
}

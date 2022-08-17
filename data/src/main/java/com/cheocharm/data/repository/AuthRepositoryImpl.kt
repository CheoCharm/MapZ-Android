package com.cheocharm.data.repository

import com.cheocharm.data.source.AuthLocalDataSource
import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

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
}

package com.cheocharm.data.repository

import com.cheocharm.data.source.AuthLocalDataSource
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override fun saveTokens(mapZSign: MapZSign) {
        authLocalDataSource.saveTokens(mapZSign)
    }

    override fun saveIsAutoSignIn(isAutoSignIn: Boolean) {
        authLocalDataSource.saveIsAutoSignIn(isAutoSignIn)
    }
}

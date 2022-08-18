package com.cheocharm.data.repository

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.source.LoginRemoteDataSource
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun requestEmailCertNumber(email: String): Result<String> {
        val result = loginRemoteDataSource.requestEmailCertNumber(email)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSign> {
        val result = loginRemoteDataSource.requestMapZSignUp(mapZSignUpRequest)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<MapZSign> {
        val result = loginRemoteDataSource.requestMapZSignIn(mapZSignInRequest)

        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

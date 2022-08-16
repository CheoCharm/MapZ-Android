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
        return loginRemoteDataSource.requestEmailCertNumber(email)
    }

    override suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSign> {
        return loginRemoteDataSource.requestMapZSignUp(mapZSignUpRequest)
    }

    override suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<MapZSign> {
        val result = loginRemoteDataSource.requestMapZSignIn(mapZSignInRequest)
        val exception = result.exceptionOrNull()

        return when(exception) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

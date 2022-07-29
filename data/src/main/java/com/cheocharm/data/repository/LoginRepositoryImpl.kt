package com.cheocharm.data.repository

import com.cheocharm.data.source.LoginRemoteDataSource
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun requestEmailCertNumber(email: String): Result<String> {
        return loginRemoteDataSource.requestEmailCertNumber(email)
    }
}

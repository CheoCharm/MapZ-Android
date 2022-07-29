package com.cheocharm.data.source

interface LoginRemoteDataSource {

    suspend fun requestEmailCertNumber(email: String): Result<String>
}

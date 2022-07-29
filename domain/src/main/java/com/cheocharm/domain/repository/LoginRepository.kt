package com.cheocharm.domain.repository

interface LoginRepository {

    suspend fun requestEmailCertNumber(email: String): Result<String>
}

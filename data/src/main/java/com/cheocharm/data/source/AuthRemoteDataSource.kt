package com.cheocharm.data.source

interface AuthRemoteDataSource {

    suspend fun refreshAccessToken(refreshToken: String): Result<String>
}

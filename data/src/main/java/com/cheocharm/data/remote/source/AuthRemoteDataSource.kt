package com.cheocharm.data.remote.source

import com.cheocharm.domain.model.Token

interface AuthRemoteDataSource {

    suspend fun refreshAccessToken(refreshToken: String): Result<Token>
}

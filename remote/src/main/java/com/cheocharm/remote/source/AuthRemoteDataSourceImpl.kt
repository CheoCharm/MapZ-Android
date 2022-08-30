package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.AuthRemoteDataSource
import com.cheocharm.domain.model.Token
import com.cheocharm.remote.api.TokenApi
import com.cheocharm.remote.mapper.toDomain
import com.cheocharm.remote.model.TokenResponse
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val tokenApi: TokenApi
) : AuthRemoteDataSource {

    override suspend fun refreshAccessToken(refreshToken: String): Result<Token> {
        val result =
            runCatching { tokenApi.refreshAccessToken(refreshToken) }
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain()
                        ?: return Result.failure(ErrorData.RefreshAccessTokenUnavailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

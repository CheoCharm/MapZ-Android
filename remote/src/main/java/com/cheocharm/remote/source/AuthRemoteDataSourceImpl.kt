package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.AuthRemoteDataSource
import com.cheocharm.remote.api.TokenApi
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val tokenApi: TokenApi
) : AuthRemoteDataSource {

    override suspend fun refreshAccessToken(refreshToken: String): Result<String> {
        val result =
            runCatching { tokenApi.refreshAccessToken(hashMapOf("refreshToken" to refreshToken)) }
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data
                        ?: return Result.failure(ErrorData.RefreshAccessTokenUnavailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

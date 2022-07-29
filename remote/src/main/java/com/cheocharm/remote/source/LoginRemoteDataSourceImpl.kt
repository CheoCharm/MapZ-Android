package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.LoginRemoteDataSource
import com.cheocharm.remote.api.LoginApi
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.NullPointerException

class LoginRemoteDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRemoteDataSource {

    override suspend fun requestEmailCertNumber(email: String): Result<String> {
        val result = runCatching { loginApi.requestEmailCertNumber(email.toRequestBody()) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val numString =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    numString.data ?: return Result.failure(
                        Throwable(
                            NullPointerException()
                        )
                    )
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

}

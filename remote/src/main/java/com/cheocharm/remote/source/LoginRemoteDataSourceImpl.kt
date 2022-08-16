package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.LoginRemoteDataSource
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.remote.api.LoginApi
import com.cheocharm.remote.mapper.toDomain
import com.cheocharm.remote.mapper.toDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.UnknownHostException
import javax.inject.Inject

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

    override suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSign> {
        val mapZSignUpDto = mapZSignUpRequest.toDto()
        val fileRequestBody = MultipartBody.Part.createFormData(
            "file",
            mapZSignUpRequest.userImage.name,
            mapZSignUpRequest.userImage.asRequestBody("image/*".toMediaType())
        )

        val result = runCatching { loginApi.signUpMapZ(mapZSignUpDto, fileRequestBody) }
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain() ?: return Result.failure(
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

    override suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<MapZSign> {
        val result = runCatching { loginApi.signInMapZ(mapZSignInRequest.toDto()) }
        println(result)
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain()
                        ?: return Result.failure(ErrorData.MapZSignInUnAvailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

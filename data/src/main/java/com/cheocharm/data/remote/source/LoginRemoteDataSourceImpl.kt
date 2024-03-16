package com.cheocharm.data.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.Token
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.data.remote.api.LoginApi
import com.cheocharm.data.remote.mapper.toDomain
import com.cheocharm.data.remote.mapper.toDto
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
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data ?: return Result.failure(
                        ErrorData.MapZCertNumberUnavailable(response.message)
                    )
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<Token> {
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
                        ErrorData.MapZSignUpUnavailable(response.message)
                    )
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<Token> {
        val result = runCatching { loginApi.signInMapZ(mapZSignInRequest.toDto()) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain()
                        ?: return Result.failure(ErrorData.MapZSignInUnavailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestGoogleSignIn(idToken: String): Result<Token> {
        val result = runCatching { loginApi.signInGoogleLogin(hashMapOf("idToken" to idToken)) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain()
                        ?: return Result.failure(ErrorData.GoogleSignInUnavailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestGoogleSignUp(googleSignUpRequest: GoogleSignUpRequest): Result<Token> {
        val fileRequestBody = MultipartBody.Part.createFormData(
            "file",
            googleSignUpRequest.userImage.name,
            googleSignUpRequest.userImage.asRequestBody("image/*".toMediaType())
        )

        val result =
            runCatching { loginApi.signUpGoogleLogin(googleSignUpRequest.toDto(), fileRequestBody) }
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain()
                        ?: return Result.failure(ErrorData.GoogleSignUpUnavailable(response.message))
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

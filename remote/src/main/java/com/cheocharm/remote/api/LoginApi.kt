package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.GoogleSignInResponse
import com.cheocharm.remote.model.GoogleSignUpResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("users")
    suspend fun signUpGoogleLogin(@Body body: RequestBody): BaseResponse<GoogleSignUpResponse>

    @POST("users/login")
    suspend fun signInGoogleLogin(@Body body: RequestBody): BaseResponse<GoogleSignInResponse>

    @POST("users/email")
    suspend fun requestEmailCertNumber(@Body body: RequestBody): BaseResponse<String>
}

package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.GoogleSignInResponse
import com.cheocharm.remote.model.GoogleSignUpResponse
import com.cheocharm.remote.model.MapZSignResponse
import com.cheocharm.remote.model.request.MapZSignInDto
import com.cheocharm.remote.model.request.MapZSignUpDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LoginApi {

    @POST("users")
    suspend fun signUpGoogleLogin(@Body body: RequestBody): BaseResponse<GoogleSignUpResponse>

    @Multipart
    @POST("users/signup")
    suspend fun signUpMapZ(
        @Part("dto") dto: MapZSignUpDto,
        @Part file: MultipartBody.Part
    ): BaseResponse<MapZSignResponse>

    @POST("users/signin")
    suspend fun signInMapZ(@Body body: MapZSignInDto): BaseResponse<MapZSignResponse>

    @POST("users/login")
    suspend fun signInGoogleLogin(@Body body: RequestBody): BaseResponse<GoogleSignInResponse>

    @POST("users/email")
    suspend fun requestEmailCertNumber(@Body body: RequestBody): BaseResponse<String>
}

package com.cheocharm.data.remote.api

import com.cheocharm.data.remote.model.BaseResponse
import com.cheocharm.data.remote.model.TokenResponse
import com.cheocharm.data.remote.model.request.GoogleSignUpDto
import com.cheocharm.data.remote.model.request.MapZSignInDto
import com.cheocharm.data.remote.model.request.MapZSignUpDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApi {

    @Multipart
    @POST("users")
    suspend fun signUpGoogleLogin(
        @Part("dto") dto: GoogleSignUpDto,
        @Part file: MultipartBody.Part
    ): BaseResponse<TokenResponse>

    @Multipart
    @POST("users/signup")
    suspend fun signUpMapZ(
        @Part("dto") dto: MapZSignUpDto,
        @Part file: MultipartBody.Part
    ): BaseResponse<TokenResponse>

    @POST("users/signin")
    suspend fun signInMapZ(@Body body: MapZSignInDto): BaseResponse<TokenResponse>

    @POST("users/login")
    suspend fun signInGoogleLogin(@Body body: HashMap<String, String>): BaseResponse<TokenResponse>

    @POST("users/email")
    suspend fun requestEmailCertNumber(@Body body: RequestBody): BaseResponse<String>
}

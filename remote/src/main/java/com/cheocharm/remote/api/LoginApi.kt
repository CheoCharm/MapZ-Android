package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.MapZSignResponse
import com.cheocharm.remote.model.request.GoogleSignUpDto
import com.cheocharm.remote.model.request.MapZSignInDto
import com.cheocharm.remote.model.request.MapZSignUpDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*

interface LoginApi {

    @POST("users")
    suspend fun signUpGoogleLogin(@Body body: GoogleSignUpDto): BaseResponse<MapZSignResponse>

    @Multipart
    @POST("users/signup")
    suspend fun signUpMapZ(
        @Part("dto") dto: MapZSignUpDto,
        @Part file: MultipartBody.Part
    ): BaseResponse<MapZSignResponse>

    @POST("users/signin")
    suspend fun signInMapZ(@Body body: MapZSignInDto): BaseResponse<MapZSignResponse>

    @POST("users/login")
    suspend fun signInGoogleLogin(@Body body: HashMap<String, String>): BaseResponse<MapZSignResponse>

    @POST("users/email")
    suspend fun requestEmailCertNumber(@Body body: RequestBody): BaseResponse<String>
}

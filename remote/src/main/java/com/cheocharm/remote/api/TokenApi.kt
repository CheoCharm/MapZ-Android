package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TokenApi {

    @GET("users/refresh")
    suspend fun refreshAccessToken(@Header("refreshToken") token: String): BaseResponse<TokenResponse>
}

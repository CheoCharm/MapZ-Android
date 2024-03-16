package com.cheocharm.data.remote.api

import com.cheocharm.data.remote.model.BaseResponse
import com.cheocharm.data.remote.model.TokenResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TokenApi {

    @GET("users/refresh")
    suspend fun refreshAccessToken(@Header("refreshToken") token: String): BaseResponse<TokenResponse>
}

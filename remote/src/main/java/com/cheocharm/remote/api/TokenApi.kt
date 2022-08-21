package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {

    @POST()
    suspend fun refreshAccessToken(@Body body: HashMap<String, String>): BaseResponse<String>
}

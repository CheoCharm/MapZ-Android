package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.response.write.MyGroup
import retrofit2.http.GET
import retrofit2.http.Query

interface WriteApi {

    @GET("group/mygroup")
    suspend fun fetchMyGroups(@Query("accessToken") accessToken: String): BaseResponse<List<MyGroup>>
}

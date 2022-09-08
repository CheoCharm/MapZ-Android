package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.response.group.GroupJoinResponse
import com.cheocharm.remote.model.response.group.GroupSearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupApi {

    @GET("group")
    suspend fun fetchGroupSearchList(
        @Query("page") page: Int,
        @Query("searchName") searchName: String
    ): BaseResponse<GroupSearchResponse>

    @POST("group/join")
    suspend fun joinGroup(@Body body: HashMap<String, String>): BaseResponse<GroupJoinResponse>
}

package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.response.group.GroupJoinResponse
import com.cheocharm.remote.model.response.group.GroupMemberResponse
import com.cheocharm.remote.model.response.group.GroupResponse
import com.cheocharm.remote.model.response.group.GroupSearchResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface GroupApi {

    @GET("group")
    suspend fun fetchGroupSearchList(
        @Query("page") page: Int,
        @Query("groupName") searchName: String,
        @Query("cursorId") cursorId: Int
    ): BaseResponse<GroupSearchResponse>

    @POST("group")
    suspend fun createGroup(@Body body: RequestBody): BaseResponse<String>

    @POST("group/join")
    suspend fun joinGroup(@Body body: HashMap<String, String>): BaseResponse<GroupJoinResponse>

    @PATCH("group/join")
    suspend fun modifyJoinGroup(@Body body: RequestBody): BaseResponse<String>

    @POST("group/invite")
    suspend fun inviteGroup(@Body body: RequestBody): BaseResponse<String>

    @PATCH("group/status")
    suspend fun modifyGroupStatus(@Body body: RequestBody): BaseResponse<String>

    @PATCH("group/exit")
    suspend fun exitGroup(@Body body: RequestBody): BaseResponse<String>

    @PATCH("group/chief")
    suspend fun modifyChief(@Body body: RequestBody): BaseResponse<String>

    @GET("group/mygroup")
    suspend fun fetchMyGroup(): BaseResponse<List<GroupResponse>>

    @GET("group/member")
    suspend fun fetchGroupMember(@Query("groupId") groupId: Int): BaseResponse<List<GroupMemberResponse>>
}

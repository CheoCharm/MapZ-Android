package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.request.WriteDiaryDto
import com.cheocharm.remote.model.request.WriteImageDto
import com.cheocharm.remote.model.response.write.MyGroup
import com.cheocharm.remote.model.response.write.WriteDiaryResponse
import com.cheocharm.remote.model.response.write.WriteImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface WriteApi {
    @GET("group/mygroup")
    suspend fun fetchMyGroups(): BaseResponse<List<MyGroup>>

    @Multipart
    @POST("diary/image")
    suspend fun writeImage(
        @Part images: List<MultipartBody.Part>,
        @Part("dto") dto: WriteImageDto
    ): BaseResponse<WriteImageResponse>

    @POST("diary/write")
    suspend fun writeDiary(@Body diary: WriteDiaryDto): BaseResponse<WriteDiaryResponse>
}

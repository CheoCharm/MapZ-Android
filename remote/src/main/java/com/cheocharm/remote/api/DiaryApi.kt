package com.cheocharm.remote.api

import com.cheocharm.remote.model.BaseResponse
import com.cheocharm.remote.model.request.WriteDiaryDto
import com.cheocharm.remote.model.request.WriteImageDto
import com.cheocharm.domain.model.WriteImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DiaryApi {
    @Multipart
    @POST("diary/image")
    suspend fun writeImage(
        @Part images: List<MultipartBody.Part>,
        @Part("dto") dto: WriteImageDto
    ): BaseResponse<WriteImageResponse>

    @POST("diary/write")
    suspend fun writeDiary(@Body diary: WriteDiaryDto): BaseResponse<Int>
}

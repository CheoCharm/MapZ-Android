package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.remote.source.WriteRemoteDataSource
import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.remote.api.DiaryApi
import com.cheocharm.remote.mapper.toDomain
import com.cheocharm.remote.mapper.toDto
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.net.UnknownHostException
import javax.inject.Inject

class WriteRemoteDataSourceImpl @Inject constructor(
    private val api: DiaryApi
) : WriteRemoteDataSource {
    override suspend fun requestWriteImages(request: WriteImageRequest): Result<TempDiary> {
        val images = request.images.map {
            MultipartBody.Part.createFormData(
                "files",
                it.name,
                it.asRequestBody("image/*".toMediaType())
            )
        }
        val result = runCatching { api.writeImage(images, request.toDto()) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))

                response.data?.toDomain()?.let {
                    Result.success(it)
                } ?: Result.failure(ErrorData.WriteImagesUnavailable(response.message))
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestWriteDiary(request: WriteDiaryRequest): Result<Long> {
        val result = runCatching { api.writeDiary(request.toDto()) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))

                response.data?.toDomain()?.let {
                    Result.success(it)
                } ?: return Result.failure(ErrorData.WriteDiaryUnavailable(response.message))
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

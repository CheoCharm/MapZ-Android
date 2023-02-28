package com.cheocharm.data.repository

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.source.WriteRemoteDataSource
import com.cheocharm.domain.model.AttachedImages
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.repository.WriteRepository
import javax.inject.Inject

class WriteRepositoryImpl @Inject constructor(
    private val writeRemoteDataSource: WriteRemoteDataSource
) : WriteRepository {
    override suspend fun requestWriteImages(request: WriteImageRequest): Result<AttachedImages> {
        val result = writeRemoteDataSource.requestWriteImages(request)

        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }

    override suspend fun requestWriteDiary(request: WriteDiaryRequest): Result<Long> {
        val result = writeRemoteDataSource.requestWriteDiary(request)

        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

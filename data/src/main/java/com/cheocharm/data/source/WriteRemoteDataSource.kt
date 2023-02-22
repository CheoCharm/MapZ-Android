package com.cheocharm.data.source

import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteDiaryResponse
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.model.WriteImageResponse

interface WriteRemoteDataSource {
    suspend fun requestWriteImages(request: WriteImageRequest): Result<WriteImageResponse>

    suspend fun requestWriteDiary(request: WriteDiaryRequest): Result<WriteDiaryResponse>
}

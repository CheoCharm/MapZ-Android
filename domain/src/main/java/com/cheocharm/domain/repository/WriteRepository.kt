package com.cheocharm.domain.repository

import com.cheocharm.domain.model.AttachedImages
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteImageRequest

interface WriteRepository {
    suspend fun requestWriteImages(request: WriteImageRequest): Result<AttachedImages>

    suspend fun requestWriteDiary(request: WriteDiaryRequest): Result<Long>
}

package com.cheocharm.domain.repository

import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteImageRequest

interface WriteRepository {
    suspend fun requestWriteImages(request: WriteImageRequest): Result<TempDiary>

    suspend fun requestWriteDiary(request: WriteDiaryRequest): Result<Long>
}

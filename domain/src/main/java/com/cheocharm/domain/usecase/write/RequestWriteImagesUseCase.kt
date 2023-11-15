package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.repository.WriteRepository
import javax.inject.Inject

class RequestWriteImagesUseCase @Inject constructor(
    private val repository: WriteRepository
) {
    suspend operator fun invoke(request: WriteImageRequest): Result<TempDiary> =
        repository.requestWriteImages(request)
}

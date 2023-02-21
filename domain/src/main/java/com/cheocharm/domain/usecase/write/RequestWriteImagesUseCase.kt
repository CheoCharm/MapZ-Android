package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.model.WriteImageResponse
import com.cheocharm.domain.repository.WriteRepository
import javax.inject.Inject

class RequestWriteImagesUseCase @Inject constructor(
    private val repository: WriteRepository
) {
    suspend operator fun invoke(request: WriteImageRequest): Result<WriteImageResponse> =
        repository.requestWriteImages(request)
}

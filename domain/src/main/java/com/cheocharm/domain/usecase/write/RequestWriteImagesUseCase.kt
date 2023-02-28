package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.AttachedImages
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.domain.repository.WriteRepository
import javax.inject.Inject

class RequestWriteImagesUseCase @Inject constructor(
    private val repository: WriteRepository
) {
    suspend operator fun invoke(request: WriteImageRequest): Result<AttachedImages> =
        repository.requestWriteImages(request)
}

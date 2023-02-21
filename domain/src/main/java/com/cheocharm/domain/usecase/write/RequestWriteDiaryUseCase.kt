package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.repository.WriteRepository
import javax.inject.Inject

class RequestWriteDiaryUseCase @Inject constructor(
    private val repository: WriteRepository
) {
    suspend operator fun invoke(request: WriteDiaryRequest): Result<Int> =
        repository.requestWriteDiary(request)
}

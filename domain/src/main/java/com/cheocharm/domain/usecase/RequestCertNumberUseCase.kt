package com.cheocharm.domain.usecase

import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class RequestCertNumberUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(email: String): Result<String> =
        loginRepository.requestEmailCertNumber(email)
}

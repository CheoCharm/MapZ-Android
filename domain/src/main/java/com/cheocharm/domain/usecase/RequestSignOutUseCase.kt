package com.cheocharm.domain.usecase

import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class RequestSignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke() {
        authRepository.removeAccessToken()
        authRepository.removeRefreshToken()
        authRepository.removeIsAutoSignIn()
    }
}

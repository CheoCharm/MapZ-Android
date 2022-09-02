package com.cheocharm.domain.usecase

import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(accessToken: String, refreshToken: String) {
        authRepository.saveTokens(accessToken, refreshToken)
    }
}

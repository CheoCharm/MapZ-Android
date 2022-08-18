package com.cheocharm.domain.usecase

import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class SaveSignInTypeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(signInType: String) {
        authRepository.saveSignInType(signInType)
    }
}

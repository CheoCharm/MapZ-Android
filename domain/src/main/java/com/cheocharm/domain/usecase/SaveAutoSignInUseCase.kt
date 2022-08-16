package com.cheocharm.domain.usecase

import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(isAutoSignIn: Boolean) {
        authRepository.saveIsAutoSignIn(isAutoSignIn)
    }
}

package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.SignInCheck
import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAutoSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): SignInCheck {
        val accessToken = authRepository.fetchAccessToken()
        val refreshToken = authRepository.fetchRefreshToken()
        val isAutoSignIn = authRepository.fetchIsAutoSignIn()
        return SignInCheck(accessToken, refreshToken, isAutoSignIn)
    }
}

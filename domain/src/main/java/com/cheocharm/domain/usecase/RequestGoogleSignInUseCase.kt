package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.Token
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class RequestGoogleSignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(idToken: String): Result<Token> {
        return loginRepository.requestGoogleSignIn(idToken)
    }
}

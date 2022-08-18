package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class RequestGoogleSignUpUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(googleSignUpRequest: GoogleSignUpRequest): Result<MapZSign> =
        loginRepository.requestGoogleSignUp(googleSignUpRequest)
}

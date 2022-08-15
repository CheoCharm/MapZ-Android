package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.MapZSignUp
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class RequestMapZSignUpUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSignUp> {
        return loginRepository.requestMapZSignUp(mapZSignUpRequest)
    }
}

package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.repository.LoginRepository
import javax.inject.Inject

class RequestMapZSignInUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){

    suspend operator fun invoke(mapZSignInRequest: MapZSignInRequest): Result<MapZSign> {
        return loginRepository.requestMapZSignIn(mapZSignInRequest)
    }
}

package com.cheocharm.domain.usecase

import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(mapZSign: MapZSign) {
        authRepository.saveTokens(mapZSign)
    }
}

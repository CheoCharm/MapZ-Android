package com.cheocharm.domain.repository

import com.cheocharm.domain.model.MapZSignUp
import com.cheocharm.domain.model.MapZSignUpRequest

interface LoginRepository {

    suspend fun requestEmailCertNumber(email: String): Result<String>

    suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSignUp>
}

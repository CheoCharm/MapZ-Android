package com.cheocharm.data.source

import com.cheocharm.domain.model.MapZSignUp
import com.cheocharm.domain.model.MapZSignUpRequest

interface LoginRemoteDataSource {

    suspend fun requestEmailCertNumber(email: String): Result<String>

    suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSignUp>
}

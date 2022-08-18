package com.cheocharm.data.source

import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest

interface LoginRemoteDataSource {

    suspend fun requestEmailCertNumber(email: String): Result<String>

    suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSign>

    suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<MapZSign>
}

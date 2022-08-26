package com.cheocharm.domain.repository

import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignUpRequest

interface LoginRepository {

    suspend fun requestEmailCertNumber(email: String): Result<String>

    suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<MapZSign>

    suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<MapZSign>

    suspend fun requestGoogleSignIn(idToken: String): Result<MapZSign>

    suspend fun requestGoogleSignUp(googleSignUpRequest: GoogleSignUpRequest): Result<MapZSign>
}

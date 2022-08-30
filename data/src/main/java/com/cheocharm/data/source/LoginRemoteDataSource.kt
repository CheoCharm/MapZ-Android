package com.cheocharm.data.source

import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.Token
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest

interface LoginRemoteDataSource {

    suspend fun requestEmailCertNumber(email: String): Result<String>

    suspend fun requestMapZSignUp(mapZSignUpRequest: MapZSignUpRequest): Result<Token>

    suspend fun requestMapZSignIn(mapZSignInRequest: MapZSignInRequest): Result<Token>

    suspend fun requestGoogleSignIn(idToken: String): Result<Token>

    suspend fun requestGoogleSignUp(googleSignUpRequest: GoogleSignUpRequest): Result<Token>
}

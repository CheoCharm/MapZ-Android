package com.cheocharm.data.remote.mapper

import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.Token
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.data.remote.model.TokenResponse
import com.cheocharm.data.remote.model.request.GoogleSignUpDto
import com.cheocharm.data.remote.model.request.MapZSignInDto
import com.cheocharm.data.remote.model.request.MapZSignUpDto

// domain -> remote
internal fun MapZSignUpRequest.toDto(): MapZSignUpDto {
    return MapZSignUpDto(email, password, username, pushAgreement)
}

// remote -> domain
internal fun TokenResponse.toDomain(): Token {
    return Token(accessToken, refreshToken)
}

// domain -> remote
internal fun MapZSignInRequest.toDto(): MapZSignInDto {
    return MapZSignInDto(email, pwd)
}

// domain -> remote
internal fun GoogleSignUpRequest.toDto(): GoogleSignUpDto {
    return GoogleSignUpDto(username, idToken, pushAgreement)
}

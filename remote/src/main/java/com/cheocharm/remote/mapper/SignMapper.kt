package com.cheocharm.remote.mapper

import com.cheocharm.domain.model.GoogleSignUpRequest
import com.cheocharm.domain.model.MapZSign
import com.cheocharm.domain.model.MapZSignInRequest
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.remote.model.MapZSignResponse
import com.cheocharm.remote.model.request.GoogleSignUpDto
import com.cheocharm.remote.model.request.MapZSignInDto
import com.cheocharm.remote.model.request.MapZSignUpDto

// domain -> remote
internal fun MapZSignUpRequest.toDto(): MapZSignUpDto {
    return MapZSignUpDto(email, password, username, pushAgreement)
}

// remote -> domain
internal fun MapZSignResponse.toDomain(): MapZSign {
    return MapZSign(accessToken, refreshToken)
}

// domain -> remote
internal fun MapZSignInRequest.toDto(): MapZSignInDto {
    return MapZSignInDto(email, pwd)
}

// domain -> remote
internal fun GoogleSignUpRequest.toDto(): GoogleSignUpDto {
    return GoogleSignUpDto(username, idToken, pushAgreement)
}

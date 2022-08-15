package com.cheocharm.remote.mapper

import com.cheocharm.data.model.MapZSignUpData
import com.cheocharm.domain.model.MapZSignUp
import com.cheocharm.domain.model.MapZSignUpRequest
import com.cheocharm.remote.model.MapZSignUpResponse
import com.cheocharm.remote.model.request.MapZSignUpDto

// domain -> remote
internal fun MapZSignUpRequest.toDto(): MapZSignUpDto {
    return MapZSignUpDto(email, password, username)
}

// remote -> domain
internal fun MapZSignUpResponse.toDomain(): MapZSignUp {
    return MapZSignUp(this.accessToken, this.refreshToken)
}

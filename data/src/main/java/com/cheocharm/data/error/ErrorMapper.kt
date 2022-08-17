package com.cheocharm.data.error

import com.cheocharm.domain.model.Error

internal fun ErrorData.toDomain(): Error = when (this) {
    ErrorData.NetworkUnavailable -> Error.NetworkUnavailable
    is ErrorData.MapZSignUpUnavailable -> Error.MapZSignUpUnavailable(message)
    is ErrorData.MapZSignInUnavailable -> Error.MapZSignInUnavailable(message)
}

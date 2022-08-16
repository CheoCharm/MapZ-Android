package com.cheocharm.data.error

import com.cheocharm.domain.model.Error

internal fun ErrorData.toDomain(): Error = when (this) {
    ErrorData.NetworkUnavailable -> Error.NetworkUnavailable
    is ErrorData.MapZSignInUnAvailable -> Error.MapZSignInAvailable(message)
}

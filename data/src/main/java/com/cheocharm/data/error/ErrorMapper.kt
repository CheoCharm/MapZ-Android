package com.cheocharm.data.error

import com.cheocharm.domain.model.Error

internal fun ErrorData.toDomain(): Error = when (this) {
    ErrorData.NetworkUnavailable -> Error.NetworkUnavailable
    is ErrorData.MapZCertNumberUnavailable -> Error.MapZCertNumberUnavailable(message)
    is ErrorData.MapZSignUpUnavailable -> Error.MapZSignUpUnavailable(message)
    is ErrorData.MapZSignInUnavailable -> Error.MapZSignInUnavailable(message)
    is ErrorData.GoogleSignInUnavailable -> Error.GoogleSignInUnavailable(message)
    is ErrorData.GoogleSignUpUnavailable -> Error.GoogleSignUpUnavailable(message)
    is ErrorData.RefreshAccessTokenUnavailable -> Error.RefreshAccessTokenUnavailable(message)
    is ErrorData.SearchGroupUnavailable -> Error.SearchGroupUnavailable(message)
    is ErrorData.JoinGroupUnavailable -> Error.JoinGroupUnavailable(message)
    is ErrorData.GetMyGroupsUnavailable -> Error.GetMyGroupsUnavailable(message)
}

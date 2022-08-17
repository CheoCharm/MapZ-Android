package com.cheocharm.domain.model

sealed class Error(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkUnavailable : Error()
    data class MapZSignUpUnavailable(override val message: String) : Error()
    data class MapZSignInUnavailable(override val message: String) : Error()
}

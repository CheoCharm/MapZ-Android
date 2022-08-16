package com.cheocharm.domain.model

sealed class Error(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkUnavailable : Error()
    data class MapZSignInAvailable(override val message: String) : Error()
}

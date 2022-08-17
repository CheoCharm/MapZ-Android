package com.cheocharm.data.error

sealed class ErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkUnavailable : ErrorData()
    data class MapZSignInUnavailable(override val message: String) : ErrorData(message)
}

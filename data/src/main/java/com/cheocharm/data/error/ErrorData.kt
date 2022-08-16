package com.cheocharm.data.error

sealed class ErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkUnavailable : ErrorData()
    data class MapZSignInUnAvailable(override val message: String) : ErrorData(message)
}

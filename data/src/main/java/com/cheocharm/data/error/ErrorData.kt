package com.cheocharm.data.error

sealed class ErrorData(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    object NetworkUnavailable : ErrorData()
    data class MapZCertNumberUnavailable(override val message: String) : ErrorData(message)
    data class MapZSignUpUnavailable(override val message: String) : ErrorData(message)
    data class MapZSignInUnavailable(override val message: String) : ErrorData(message)
    data class GoogleSignInUnavailable(override val message: String) : ErrorData(message)
    data class GoogleSignUpUnavailable(override val message: String) : ErrorData(message)
    data class RefreshAccessTokenUnavailable(override val message: String) : ErrorData(message)
    data class SearchGroupUnavailable(override val message: String) : ErrorData(message)
    data class JoinGroupUnavailable(override val message: String) : ErrorData(message)
    data class GroupCreateUnavailable(override val message: String) : ErrorData(message)
}

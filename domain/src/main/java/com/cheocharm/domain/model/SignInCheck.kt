package com.cheocharm.domain.model

data class SignInCheck(
    val accessToken: String?,
    val refreshToken: String?,
    val isAutoSignIn: Boolean
)

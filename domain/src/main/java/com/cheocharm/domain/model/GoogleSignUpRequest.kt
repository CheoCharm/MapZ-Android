package com.cheocharm.domain.model

data class GoogleSignUpRequest(
    val username: String,
    val idToken: String,
    val pushAgreement: Boolean
)

package com.cheocharm.remote.model.request

data class GoogleSignUpDto(
    val username: String,
    val idToken: String,
    val pushAgreement: Boolean
)

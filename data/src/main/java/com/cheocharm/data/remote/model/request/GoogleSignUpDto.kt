package com.cheocharm.data.remote.model.request

data class GoogleSignUpDto(
    val username: String,
    val idToken: String,
    val pushAgreement: Boolean
)

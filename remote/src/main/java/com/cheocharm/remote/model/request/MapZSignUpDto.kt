package com.cheocharm.remote.model.request

data class MapZSignUpDto(
    val email: String,
    val password: String,
    val username: String,
    val pushAgreement: Boolean
)

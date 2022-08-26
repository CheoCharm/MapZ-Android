package com.cheocharm.domain.model

import java.io.File

data class GoogleSignUpRequest(
    val username: String,
    val idToken: String,
    val pushAgreement: Boolean,
    val userImage: File
)

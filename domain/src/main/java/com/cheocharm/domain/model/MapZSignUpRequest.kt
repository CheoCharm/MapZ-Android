package com.cheocharm.domain.model

import java.io.File

data class MapZSignUpRequest(
    val email: String,
    val password: String,
    val username: String,
    val userImage: File
)

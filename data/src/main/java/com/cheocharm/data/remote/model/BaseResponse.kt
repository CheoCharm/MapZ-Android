package com.cheocharm.data.remote.model

data class BaseResponse<T>(
    val statusCode: Int,
    val customCode: Int,
    val data: T?,
    val message: String
)

package com.cheocharm.remote.model.request

data class WriteImageDto(
    val groupId: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double
)

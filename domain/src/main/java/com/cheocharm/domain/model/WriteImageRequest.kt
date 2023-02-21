package com.cheocharm.domain.model

import java.io.File

data class WriteImageRequest(
    val groupId: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<File>
)

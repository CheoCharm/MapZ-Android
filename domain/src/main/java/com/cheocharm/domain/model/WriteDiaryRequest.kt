package com.cheocharm.domain.model

data class WriteDiaryRequest(
    val id: Int,
    val title: String,
    val content: String
)

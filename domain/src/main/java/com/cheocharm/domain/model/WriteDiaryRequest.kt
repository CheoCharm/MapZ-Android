package com.cheocharm.domain.model

data class WriteDiaryRequest(
    val id: Long,
    val title: String,
    val content: String
)

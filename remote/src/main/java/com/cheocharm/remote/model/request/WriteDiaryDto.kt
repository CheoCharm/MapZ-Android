package com.cheocharm.remote.model.request

data class WriteDiaryDto(
    val diaryId: Long,
    val title: String,
    val content: String
)

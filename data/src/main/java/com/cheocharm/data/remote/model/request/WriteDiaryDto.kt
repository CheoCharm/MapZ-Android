package com.cheocharm.data.remote.model.request

data class WriteDiaryDto(
    val diaryId: Long,
    val title: String,
    val content: String
)

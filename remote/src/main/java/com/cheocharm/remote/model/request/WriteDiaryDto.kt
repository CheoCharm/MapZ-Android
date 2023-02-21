package com.cheocharm.remote.model.request

import com.google.gson.annotations.SerializedName

data class WriteDiaryDto(
    @SerializedName("diary_id") val id: Int,
    val title: String,
    @SerializedName("html_content") val html: String
)

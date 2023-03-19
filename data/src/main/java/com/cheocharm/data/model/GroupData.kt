package com.cheocharm.data.model

data class GroupData(
    val groupName: String,
    val groupImageUrl: String?,
    val count: Int,
    val groupMembersImageUrls: List<String>,
    val groupId: Int
)

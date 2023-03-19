package com.cheocharm.remote.model.request

data class GroupCreateDto(
    val groupName: String,
    val bio: String,
    val changeStatus: Boolean
)

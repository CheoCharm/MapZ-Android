package com.cheocharm.remote.model.response.group

data class GroupMemberResponse(
    val userName: String,
    val userImageUrl: String,
    val userId: Int,
    val invitationStatus: String
)

package com.cheocharm.domain.model

data class Group(
    val id: Int,
    val name: String,
    val bio: String,
    val createdAt: String,
    val members: List<GroupMember>,
    val numberOfMembers: Int,
    val groupImageUrl: String? = null
)

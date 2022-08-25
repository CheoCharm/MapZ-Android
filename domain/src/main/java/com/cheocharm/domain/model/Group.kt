package com.cheocharm.domain.model

data class Group(
    val name: String,
    val members: List<GroupMember>,
    val numberOfMembers: Int,
    val groupImageUrl: String? = null
)

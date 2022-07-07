package com.cheocharm.domain

data class Group(
    val name: String,
    val members: List<GroupMember>,
    val groupImageUrl: String? = null
)

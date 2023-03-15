package com.cheocharm.presentation.model

import com.cheocharm.domain.model.Group

data class GroupModel(
    val id: Int,
    val name: String,
    val bio: String,
    val createdAt: String,
    val members: List<GroupMemberModel>,
    val numberOfMembers: Int,
    val groupImageUrl: String? = null
)

fun Group.toPresentation(): GroupModel = GroupModel(
    id,
    name,
    bio,
    createdAt,
    members.map { it.toPresentation() },
    numberOfMembers,
    groupImageUrl
)

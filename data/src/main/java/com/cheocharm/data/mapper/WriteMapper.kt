package com.cheocharm.data.mapper

import com.cheocharm.data.model.GroupData
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember

internal fun GroupData.toDomain(): Group =
    Group(
        groupId,
        groupName,
        "",
        "",
        groupMembersImageUrls.map { GroupMember(it) },
        groupMembersImageUrls.size,
        groupImageUrl
    )

internal fun com.cheocharm.data.local.model.Group.toDomain(): Group = Group(
    id, name, bio, createdAt, members.map { GroupMember(it.imageUrl) }, numberOfMembers, groupImageUrl
)

package com.cheocharm.data.mapper

import com.cheocharm.data.model.GroupData
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember

internal fun GroupData.toDomain(): Group =
    Group(
        groupName,
        "",
        "",
        groupMembersImageUrls.map { GroupMember(it) },
        groupMembersImageUrls.size,
        groupImageUrl
    )

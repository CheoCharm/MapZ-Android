package com.cheocharm.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["groupId", "memberId"])
data class GroupMemberCrossRef(
    val groupId: Int,
    val memberId: Int
)

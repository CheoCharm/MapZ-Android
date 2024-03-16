package com.cheocharm.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GroupWithMembers(
    @Embedded val group: Group,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "memberId",
        associateBy = Junction(GroupMemberCrossRef::class)
    )
    val members: List<GroupMember>
)

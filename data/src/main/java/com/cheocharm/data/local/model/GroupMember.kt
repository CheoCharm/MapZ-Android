package com.cheocharm.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupMember(
    @PrimaryKey val memberId: Int,
    val imageUrl: String? = null
)

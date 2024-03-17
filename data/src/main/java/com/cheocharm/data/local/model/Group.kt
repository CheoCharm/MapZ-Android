package com.cheocharm.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val bio: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    val members: List<GroupMember>,
    @ColumnInfo(name = "number_of_members") val numberOfMembers: Int,
    @ColumnInfo(name = "group_image_url") val groupImageUrl: String? = null
)

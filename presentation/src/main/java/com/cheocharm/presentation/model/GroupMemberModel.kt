package com.cheocharm.presentation.model

import com.cheocharm.domain.model.GroupMember

data class GroupMemberModel(val imageUrl: String?)

fun GroupMember.toPresentation(): GroupMemberModel = GroupMemberModel(imageUrl)

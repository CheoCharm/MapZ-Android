package com.cheocharm.domain.model.group

import java.io.File

data class GroupCreateRequest(
    val groupImage: File,
    val groupName: String,
    val groupBio: String,
    val changeStatus: Boolean
)

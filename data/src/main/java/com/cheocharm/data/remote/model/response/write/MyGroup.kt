package com.cheocharm.data.remote.model.response.write

data class MyGroup(
    val groupName: String,
    val groupImageUrl: String?,
    val count: Int,
    val chiefUserImage: String,
    val groupId: Int
)

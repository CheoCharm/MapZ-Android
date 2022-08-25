package com.cheocharm.remote.model.response.group

data class GroupResponse(
    val count: Int,
    val groupImageUrl: String,
    val groupName: String,
    val userImageUrlList: List<String>
)

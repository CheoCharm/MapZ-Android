package com.cheocharm.remote.model.response.group

data class GroupSearchResponse(
    val hasNextPage: Boolean,
    val groupList: List<GroupResponse>
)

package com.cheocharm.data.remote.model.response.group

import com.cheocharm.data.remote.model.response.group.GroupResponse

data class GroupSearchResponse(
    val hasNextPage: Boolean,
    val groupList: List<GroupResponse>
)

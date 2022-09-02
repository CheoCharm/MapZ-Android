package com.cheocharm.domain.model

data class GroupSearch(
    val hasNextPage: Boolean,
    val groupList: List<Group>
)

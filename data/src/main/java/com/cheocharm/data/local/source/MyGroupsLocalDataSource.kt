package com.cheocharm.data.local.source

import com.cheocharm.domain.model.Group

interface MyGroupsLocalDataSource {
    suspend fun getMyGroups(): List<Group>

    suspend fun joinGroup(group: com.cheocharm.data.local.model.Group)

    suspend fun clearMyGroups()
}

package com.cheocharm.data.local.source

import com.cheocharm.data.local.model.GroupDao
import com.cheocharm.data.mapper.toDomain
import com.cheocharm.domain.model.Group
import javax.inject.Inject

class MyGroupsLocalDataSourceImpl @Inject constructor(
    private val groupDao: GroupDao
): MyGroupsLocalDataSource {
    override suspend fun getMyGroups(): List<Group> {
        return groupDao.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun joinGroup(group: com.cheocharm.data.local.model.Group) {
        groupDao.insertAll(group)
    }

    override suspend fun clearMyGroups() {
        groupDao.clear()
    }
}

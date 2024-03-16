package com.cheocharm.data.repository

import androidx.paging.PagingData
import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.local.source.MyGroupsLocalDataSource
import com.cheocharm.data.remote.source.GroupRemoteDataSource
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember
import com.cheocharm.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource,
    private val myGroupsLocalDataSource: MyGroupsLocalDataSource
) : GroupRepository {
    private var count = 0

    override suspend fun createGroup(userId: Int, name: String, bio: String, groupImageUrl: String) {
        val group = com.cheocharm.data.local.model.Group(
            count++,
            name,
            "",
            "",
            listOf(com.cheocharm.data.local.model.GroupMember(userId)),
            1
        )
        myGroupsLocalDataSource.joinGroup(group)
    }

    override fun searchGroup(searchGroupName: String): Flow<PagingData<Group>> {
        return groupRemoteDataSource.fetchGroupSearchList(searchGroupName)
    }

    override suspend fun joinGroup(groupName: String): Result<Unit> {
        val result = groupRemoteDataSource.joinGroup(groupName)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

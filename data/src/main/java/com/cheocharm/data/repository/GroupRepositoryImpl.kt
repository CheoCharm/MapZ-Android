package com.cheocharm.data.repository

import androidx.paging.PagingData
import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.source.GroupRemoteDataSource
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupJoin
import com.cheocharm.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource
) : GroupRepository {

    override fun searchGroup(searchGroupName: String): Flow<PagingData<Group>> {
        return groupRemoteDataSource.fetchGroupSearchList(searchGroupName)
    }

    override suspend fun joinGroup(groupName: String): Result<GroupJoin> {
        val result = groupRemoteDataSource.joinGroup(groupName)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

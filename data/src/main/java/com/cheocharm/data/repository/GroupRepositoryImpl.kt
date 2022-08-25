package com.cheocharm.data.repository

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.error.toDomain
import com.cheocharm.data.source.GroupRemoteDataSource
import com.cheocharm.domain.model.GroupSearch
import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupRemoteDataSource: GroupRemoteDataSource
) : GroupRepository {

    override suspend fun searchGroup(page: Int, searchGroupName: String): Result<GroupSearch> {
        val result = groupRemoteDataSource.fetchGroupSearchList(page, searchGroupName)
        return when (val exception = result.exceptionOrNull()) {
            is ErrorData -> Result.failure(exception.toDomain())
            null -> result
            else -> Result.failure(exception)
        }
    }
}

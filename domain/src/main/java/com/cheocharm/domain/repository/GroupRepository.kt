package com.cheocharm.domain.repository

import androidx.paging.PagingData
import com.cheocharm.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun searchGroup(searchGroupName: String): Flow<PagingData<Group>>

    suspend fun joinGroup(groupName: String): Result<Unit>
}

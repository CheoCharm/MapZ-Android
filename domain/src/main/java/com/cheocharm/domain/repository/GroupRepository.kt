package com.cheocharm.domain.repository

import androidx.paging.PagingData
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupJoin
import com.cheocharm.domain.model.group.GroupCreateRequest
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun searchGroup(searchGroupName: String): Flow<PagingData<Group>>

    suspend fun joinGroup(groupName: String): Result<GroupJoin>

    suspend fun createGroup(groupCreateRequest: GroupCreateRequest): Result<String>
}

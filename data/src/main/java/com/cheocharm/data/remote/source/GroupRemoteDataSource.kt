package com.cheocharm.data.remote.source

import androidx.paging.PagingData
import com.cheocharm.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRemoteDataSource {

    fun fetchGroupSearchList(searchGroupName: String): Flow<PagingData<Group>>

    suspend fun joinGroup(groupName: String): Result<Unit>
}

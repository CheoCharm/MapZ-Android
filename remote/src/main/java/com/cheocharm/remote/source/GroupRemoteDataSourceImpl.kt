package com.cheocharm.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.remote.source.GroupRemoteDataSource
import com.cheocharm.domain.model.Group
import com.cheocharm.remote.api.GroupApi
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException
import javax.inject.Inject

class GroupRemoteDataSourceImpl @Inject constructor(
    private val groupApi: GroupApi
) : GroupRemoteDataSource {

    override fun fetchGroupSearchList(searchGroupName: String): Flow<PagingData<Group>> {
        return Pager(PagingConfig(10)) {
            GroupSearchPagingSource(groupApi, searchGroupName)
        }.flow
    }

    override suspend fun joinGroup(groupName: String): Result<Unit> {
        val result = runCatching { groupApi.joinGroup(hashMapOf("groupName" to groupName)) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                if (response.statusCode == 200) Result.success(Unit)
                else Result.failure(ErrorData.JoinGroupUnavailable(response.message))
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

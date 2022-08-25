package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.GroupRemoteDataSource
import com.cheocharm.domain.model.GroupSearch
import com.cheocharm.remote.api.GroupApi
import com.cheocharm.remote.mapper.toDomain
import java.net.UnknownHostException
import javax.inject.Inject

class GroupRemoteDataSourceImpl @Inject constructor(
    private val groupApi: GroupApi
) : GroupRemoteDataSource {

    override suspend fun fetchGroupSearchList(
        page: Int,
        searchGroupName: String
    ): Result<GroupSearch> {
        val result = runCatching { groupApi.fetchGroupSearchList(page, searchGroupName) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain() ?: return Result.failure(
                        ErrorData.SearchGroupUnavailable(response.message)
                    )
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

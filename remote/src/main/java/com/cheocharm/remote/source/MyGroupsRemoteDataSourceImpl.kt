package com.cheocharm.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.model.GroupData
import com.cheocharm.data.source.MyGroupsRemoteDataSource
import com.cheocharm.remote.api.WriteApi
import com.cheocharm.remote.mapper.toData
import java.net.UnknownHostException
import javax.inject.Inject

class MyGroupsRemoteDataSourceImpl @Inject constructor(
    private val writeApi: WriteApi
) : MyGroupsRemoteDataSource {

    override suspend fun getMyGroups(accessToken: String): Result<List<GroupData>> {
        val result = runCatching { writeApi.fetchMyGroups(accessToken) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                val data = response.data?.map { it.toData() }
                    ?: return Result.failure(ErrorData.GetMyGroupsUnavailable(response.message))

                Result.success(data)
            }
            is UnknownHostException -> return Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

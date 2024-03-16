package com.cheocharm.data.remote.source

import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.model.GroupData
import com.cheocharm.data.remote.api.DiaryApi
import com.cheocharm.data.remote.mapper.toData
import java.net.UnknownHostException
import javax.inject.Inject

class MyGroupsRemoteDataSourceImpl @Inject constructor(
    private val diaryApi: DiaryApi
) : MyGroupsRemoteDataSource {

    override suspend fun getMyGroups(): Result<List<GroupData>> {
        val result = runCatching { diaryApi.fetchMyGroups() }

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

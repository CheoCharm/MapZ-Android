package com.cheocharm.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cheocharm.data.error.ErrorData
import com.cheocharm.data.source.GroupRemoteDataSource
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupJoin
import com.cheocharm.domain.model.group.GroupCreateRequest
import com.cheocharm.remote.api.GroupApi
import com.cheocharm.remote.mapper.toDomain
import com.cheocharm.remote.mapper.toDto
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

    override suspend fun joinGroup(groupName: String): Result<GroupJoin> {
        val result = runCatching { groupApi.joinGroup(hashMapOf("groupName" to groupName)) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    response.data?.toDomain() ?: return Result.failure(
                        ErrorData.JoinGroupUnavailable(response.message)
                    )
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }

    override suspend fun createGroup(groupCreateRequest: GroupCreateRequest): Result<String> {
        val groupCreateDto = groupCreateRequest.toDto()
        val fileRequestBody = MultipartBody.Part.createFormData(
            "file",
            groupCreateRequest.groupImage.name,
            groupCreateRequest.groupImage.asRequestBody("image/*".toMediaType())
        )

        val result = runCatching { groupApi.createGroup(groupCreateDto, fileRequestBody) }
        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull() ?: return Result.failure(Throwable(NullPointerException()))
                Result.success(
                    if (response.statusCode != 200) return Result.failure(
                        ErrorData.GroupCreateUnavailable(response.message)
                    ) else response.data ?: ""
                )
            }
            is UnknownHostException -> Result.failure(ErrorData.NetworkUnavailable)
            else -> Result.failure(exception)
        }
    }
}

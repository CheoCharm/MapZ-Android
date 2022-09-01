package com.cheocharm.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cheocharm.data.error.ErrorData
import com.cheocharm.domain.model.Group
import com.cheocharm.remote.api.GroupApi
import com.cheocharm.remote.mapper.toDomain
import java.net.UnknownHostException

class GroupSearchPagingSource(
    private val groupApi: GroupApi,
    private val groupSearchName: String
) : PagingSource<Int, Group>() {

    override fun getRefreshKey(state: PagingState<Int, Group>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Group> {
        val start = params.key ?: 0
        val result = runCatching { groupApi.fetchGroupSearchList(start, groupSearchName) }

        return when (val exception = result.exceptionOrNull()) {
            null -> {
                val response =
                    result.getOrNull()
                        ?: return LoadResult.Error(Throwable(NullPointerException()))
                LoadResult.Page(
                    response.data?.toDomain()?.groupList ?: return LoadResult.Error(
                        ErrorData.SearchGroupUnavailable(response.message)
                    ),
                    if (start == 0) null else start - 1,
                    if (response.data.hasNextPage) start + 1 else null
                )
            }
            is UnknownHostException -> LoadResult.Error(ErrorData.NetworkUnavailable)
            else -> LoadResult.Error(exception)
        }
    }
}

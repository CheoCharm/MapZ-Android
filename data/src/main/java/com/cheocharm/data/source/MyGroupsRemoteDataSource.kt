package com.cheocharm.data.source

import com.cheocharm.data.model.GroupData

interface MyGroupsRemoteDataSource {

    suspend fun getMyGroups(accessToken: String): Result<List<GroupData>>
}

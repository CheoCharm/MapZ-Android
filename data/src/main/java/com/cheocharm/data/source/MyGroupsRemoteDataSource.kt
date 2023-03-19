package com.cheocharm.data.source

import com.cheocharm.data.model.GroupData

interface MyGroupsRemoteDataSource {

    suspend fun getMyGroups(): Result<List<GroupData>>
}

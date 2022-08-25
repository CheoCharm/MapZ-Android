package com.cheocharm.data.source

import com.cheocharm.domain.model.GroupSearch

interface GroupRemoteDataSource {

    suspend fun fetchGroupSearchList(page: Int, searchGroupName: String): Result<GroupSearch>
}

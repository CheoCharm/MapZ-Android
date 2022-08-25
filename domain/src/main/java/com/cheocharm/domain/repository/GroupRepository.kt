package com.cheocharm.domain.repository

import com.cheocharm.domain.model.GroupSearch

interface GroupRepository {

    suspend fun searchGroup(page: Int, searchGroupName: String): Result<GroupSearch>
}

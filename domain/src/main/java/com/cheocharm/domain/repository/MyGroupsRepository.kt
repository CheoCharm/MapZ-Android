package com.cheocharm.domain.repository

import com.cheocharm.domain.model.Group

interface MyGroupsRepository {

    suspend fun getMyGroups(): Result<List<Group>>
}

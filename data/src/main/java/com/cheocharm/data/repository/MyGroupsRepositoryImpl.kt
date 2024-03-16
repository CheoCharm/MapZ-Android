package com.cheocharm.data.repository

import com.cheocharm.data.local.source.MyGroupsLocalDataSource
import com.cheocharm.data.remote.source.MyGroupsRemoteDataSource
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.repository.MyGroupsRepository
import javax.inject.Inject

class MyGroupsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MyGroupsRemoteDataSource,
    private val localDataSource: MyGroupsLocalDataSource
) : MyGroupsRepository {

    override suspend fun getMyGroups(): Result<List<Group>> {
//        val result = remoteDataSource.getMyGroups().mapCatching {
//            it.map { data ->
//                data.toDomain()
//            }
//        }
//        val exception = result.exceptionOrNull()
//
//        return if (exception is ErrorData) {
//            Result.failure(exception.toDomain())
//        } else {
//            result
//        }

        return getMyGroupsFromLocalDatabase()
    }

    private suspend fun getMyGroupsFromLocalDatabase(): Result<List<Group>> {
        val myGroups = localDataSource.getMyGroups()

        return Result.success(myGroups)
    }
}

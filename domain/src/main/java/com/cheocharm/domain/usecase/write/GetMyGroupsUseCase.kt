package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.repository.AuthRepository
import com.cheocharm.domain.repository.MyGroupsRepository
import javax.inject.Inject

class GetMyGroupsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val myGroupsRepository: MyGroupsRepository
) {

    suspend operator fun invoke(): Result<List<Group>> = runCatching {
        val accessToken = authRepository.fetchAccessToken()
        return if (accessToken == null) {
            Result.failure(Error.GetMyGroupsUnavailable("Access token is null"))
        } else {
            myGroupsRepository.getMyGroups(accessToken)
        }
    }
}

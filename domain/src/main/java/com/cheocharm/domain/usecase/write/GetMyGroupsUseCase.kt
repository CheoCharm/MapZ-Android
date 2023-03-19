package com.cheocharm.domain.usecase.write

import com.cheocharm.domain.model.Group
import com.cheocharm.domain.repository.MyGroupsRepository
import javax.inject.Inject

class GetMyGroupsUseCase @Inject constructor(
    private val myGroupsRepository: MyGroupsRepository
) {

    suspend operator fun invoke(): Result<List<Group>> = myGroupsRepository.getMyGroups()
}

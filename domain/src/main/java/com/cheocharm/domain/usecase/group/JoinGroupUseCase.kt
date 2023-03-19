package com.cheocharm.domain.usecase.group

import com.cheocharm.domain.model.GroupJoin
import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class JoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupName: String): Result<GroupJoin> {
        return groupRepository.joinGroup(groupName)
    }
}

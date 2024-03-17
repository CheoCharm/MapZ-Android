package com.cheocharm.domain.usecase.group

import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class ClearMyGroupsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke() {
        return groupRepository.clearMyGroups()
    }
}

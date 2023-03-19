package com.cheocharm.domain.usecase.group

import com.cheocharm.domain.model.group.GroupCreateRequest
import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class GroupCreateUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupCreateRequest: GroupCreateRequest): Result<String> {
        return groupRepository.createGroup(groupCreateRequest)
    }
}

package com.cheocharm.domain.usecase.group

import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        userId: Int,
        name: String,
        bio: String,
        groupImageUrl: String
    ) {
        return groupRepository.createGroup(userId, name, bio, groupImageUrl)
    }
}

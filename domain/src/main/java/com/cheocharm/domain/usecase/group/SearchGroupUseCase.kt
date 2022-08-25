package com.cheocharm.domain.usecase.group

import com.cheocharm.domain.model.GroupSearch
import com.cheocharm.domain.repository.GroupRepository
import javax.inject.Inject

class SearchGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    suspend operator fun invoke(page: Int, searchGroupName: String): Result<GroupSearch> {
        return groupRepository.searchGroup(page, searchGroupName)
    }
}

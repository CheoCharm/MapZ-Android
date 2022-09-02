package com.cheocharm.domain.usecase.group

import androidx.paging.PagingData
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(searchGroupName: String): Flow<PagingData<Group>> {
        return groupRepository.searchGroup(searchGroupName)
    }
}

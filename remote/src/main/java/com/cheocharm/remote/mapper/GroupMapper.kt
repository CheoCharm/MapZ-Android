package com.cheocharm.remote.mapper

import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember
import com.cheocharm.domain.model.GroupSearch
import com.cheocharm.remote.model.response.group.GroupSearchResponse

// remote -> domain
internal fun GroupSearchResponse.toDomain(): GroupSearch {
    val groupResultList = groupList.map {
        val groupMemberList = it.userImageUrlList.map { url ->
            GroupMember(url)
        }
        Group(it.groupName, groupMemberList, it.count, it.groupImageUrl)
    }
    return GroupSearch(hasNextPage, groupResultList)
}

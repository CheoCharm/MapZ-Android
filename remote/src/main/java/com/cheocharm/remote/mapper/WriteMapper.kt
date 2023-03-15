package com.cheocharm.remote.mapper

import com.cheocharm.data.model.GroupData
import com.cheocharm.remote.model.response.write.MyGroup

internal fun MyGroup.toData(): GroupData {
    return GroupData(groupName, groupImageUrl, count, listOf(chiefUserImage), groupId)
}

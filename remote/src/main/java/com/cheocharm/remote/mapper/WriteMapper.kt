package com.cheocharm.remote.mapper

import com.cheocharm.data.model.GroupData
import com.cheocharm.domain.model.TempDiary
import com.cheocharm.domain.model.WriteDiaryRequest
import com.cheocharm.domain.model.WriteImageRequest
import com.cheocharm.remote.model.request.WriteDiaryDto
import com.cheocharm.remote.model.request.WriteImageDto
import com.cheocharm.remote.model.response.write.MyGroup
import com.cheocharm.remote.model.response.write.WriteDiaryResponse
import com.cheocharm.remote.model.response.write.WriteImageResponse

internal fun MyGroup.toData(): GroupData {
    return GroupData(groupName, groupImageUrl, count, listOf(chiefUserImage), groupId)
}

internal fun WriteImageRequest.toDto(): WriteImageDto =
    WriteImageDto(groupId, address, latitude, longitude)

internal fun WriteDiaryRequest.toDto(): WriteDiaryDto = WriteDiaryDto(id, title, content)

internal fun WriteImageResponse.toDomain(): TempDiary = TempDiary(diaryId, imageURLs)

internal fun WriteDiaryResponse.toDomain(): Long = diaryId

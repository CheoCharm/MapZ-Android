package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor() : ViewModel() {
    private val _groups = MutableLiveData(
        listOf(
            Group(
                "그룹제목 1",
                "맵지 고등학교 추억 교환일기!",
                "2022.02.23",
                listOf(
                    GroupMember(),
                    GroupMember(),
                    GroupMember(),
                    GroupMember(),
                    GroupMember(),
                    GroupMember(),
                    GroupMember(),
                    GroupMember()
                ), 4
            ),
            Group(
                "그룹제목 2", "맵지 고등학교 추억 교환일기!",
                "2022.02.23",
                listOf(
                    GroupMember(), GroupMember(), GroupMember()
                ), 0
            ),
            Group(
                "그룹제목 3", "맵지 고등학교 추억 교환일기!",
                "2022.02.23",
                listOf(
                    GroupMember(), GroupMember(), GroupMember()
                ), 0
            ),
        )
    )
    val groups: LiveData<List<Group>> = _groups
}

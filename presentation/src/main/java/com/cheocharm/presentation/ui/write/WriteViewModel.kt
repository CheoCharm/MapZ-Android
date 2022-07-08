package com.cheocharm.presentation.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheocharm.presentation.model.Group
import com.cheocharm.presentation.model.GroupMember
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor() : ViewModel() {
    private val _groups = MutableLiveData(
        listOf(
            Group(
                "그룹제목 1", listOf(
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
                "그룹제목 2", listOf(
                    GroupMember(), GroupMember(), GroupMember()
                ), 0
            ),
            Group(
                "그룹제목 3", listOf(
                    GroupMember(), GroupMember(), GroupMember()
                ), 0
            ),
        )
    )
    val groups: LiveData<List<Group>> = _groups
}

package com.cheocharm.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.usecase.group.SearchGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember
import com.cheocharm.domain.usecase.group.JoinGroupUseCase
import com.cheocharm.presentation.common.Event
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGroupUseCase: SearchGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase
) : ViewModel() {

    private val _searchGroupName = MutableLiveData<String>()
    val searchGroupName: LiveData<String>
        get() = _searchGroupName

    private val _searchGroupHasNextPage = MutableLiveData<Boolean>()
    val searchGroupHasNextPage: LiveData<Boolean>
        get() = _searchGroupHasNextPage

    private var page = 0

    private val _groupSearchResultList = MutableLiveData<List<Group>>()
    val groupSearchResultList: LiveData<List<Group>>
        get() = _groupSearchResultList

    private val _selectedGroup = MutableLiveData<Group>()
    val selectedGroup: LiveData<Group>
        get() = _selectedGroup

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>>
        get() = _toastMessage

    fun setSearchGroupName(groupName: String) {
        _searchGroupName.value = groupName
    }

    fun setSelectedGroup(group: Group) {
        _selectedGroup.value = group
    }

    fun searchGroup(searchGroupName: String) {
        viewModelScope.launch {
            searchGroupUseCase.invoke(page, searchGroupName)
                .onSuccess { groupSearch ->
                    _searchGroupHasNextPage.value = groupSearch.hasNextPage
                    if (groupSearch.hasNextPage) page += 1
                    _groupSearchResultList.value = groupSearch.groupList
                }
                .onFailure { throwable ->
                    when (throwable) {
                        is Error.SearchGroupUnavailable -> setToastMessage(throwable.message)
                        else -> setToastMessage("그룹 검색에 실패하였습니다.")
                    }

                    _groupSearchResultList.value = listOf(
                        Group(
                            "그룹1",
                            listOf(
                                GroupMember(),
                                GroupMember()
                            ),
                            0,
                            "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
                        ),
                        Group(
                            "그룹2", listOf(
                                GroupMember("https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGNoYW5nZXxlbnwwfHwwfHw%3D&w=1000&q=80"),
                                GroupMember("https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGNoYW5nZXxlbnwwfHwwfHw%3D&w=1000&q=80"),
                                GroupMember("https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGNoYW5nZXxlbnwwfHwwfHw%3D&w=1000&q=80"),
                                GroupMember("https://images.unsplash.com/photo-1453728013993-6d66e9c9123a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGNoYW5nZXxlbnwwfHwwfHw%3D&w=1000&q=80")
                            ), 3
                        ),
                        Group(
                            "그룹3", listOf(
                                GroupMember(),
                                GroupMember(),
                                GroupMember(),
                                GroupMember(),
                                GroupMember(),
                                GroupMember()
                            ), 10
                        )
                    )
                }
        }
    }

    fun joinGroup() {
        viewModelScope.launch {
            selectedGroup.value?.name?.let {
                joinGroupUseCase.invoke(it)
                    .onSuccess {
                        // TODO: 바텀 팝업 띄우기
                    }.onFailure { throwable ->
                        when (throwable) {
                            is Error.JoinGroupUnavailable -> setToastMessage(throwable.message)
                            else -> setToastMessage("그룹 가입 요청을 실패하였습니다.")
                        }
                    }
            }
        }
    }

    private fun setToastMessage(message: String) {
        _toastMessage.value = Event(message)
    }
}

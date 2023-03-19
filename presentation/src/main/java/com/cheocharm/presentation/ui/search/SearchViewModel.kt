package com.cheocharm.presentation.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cheocharm.domain.model.Error
import com.cheocharm.domain.model.Group
import com.cheocharm.domain.usecase.group.JoinGroupUseCase
import com.cheocharm.domain.usecase.group.SearchGroupUseCase
import com.cheocharm.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGroupUseCase: SearchGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase
) : ViewModel() {

    private val _searchGroupName = MutableLiveData<String>()
    val searchGroupName: LiveData<String>
        get() = _searchGroupName

    val groupSearchResultList = searchGroupName.switchMap { groupName ->
        searchGroupUseCase.invoke(groupName).cachedIn(viewModelScope).asLiveData()
    }

    private val _selectedGroup = MutableLiveData<Group>()
    val selectedGroup: LiveData<Group>
        get() = _selectedGroup

    private val _searchGroupJoinBottom = MutableLiveData<Event<Unit>>()
    val searchGroupJoinBottom: LiveData<Event<Unit>>
        get() = _searchGroupJoinBottom

    private val _toastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>>
        get() = _toastMessage

    fun setSearchGroupName(groupName: String) {
        _searchGroupName.value = groupName
    }

    fun setSelectedGroup(group: Group) {
        _selectedGroup.value = group
    }

    fun joinGroup() {
        viewModelScope.launch {
            selectedGroup.value?.name?.let {
                joinGroupUseCase.invoke(it)
                    .onSuccess { groupJoin ->
                        if (groupJoin.alreadyJoin.not()) _searchGroupJoinBottom.value = Event(Unit)
                        else setToastMessage(groupJoin.status)
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

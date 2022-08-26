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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGroupUseCase: SearchGroupUseCase
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

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun setSearchGroupName(groupName: String) {
        _searchGroupName.value = groupName
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
                }
        }
    }

    private fun setToastMessage(message: String) {
        _toastMessage.value = message
    }
}

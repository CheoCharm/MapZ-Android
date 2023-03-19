package com.cheocharm.presentation.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheocharm.domain.model.group.GroupCreateRequest
import com.cheocharm.domain.model.group.GroupCreateSearchResult
import com.cheocharm.domain.usecase.group.GroupCreateUseCase
import com.cheocharm.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class GroupCreateViewModel @Inject constructor(
    private val groupCreateUseCase: GroupCreateUseCase
) : ViewModel() {

    private val _groupImage = MutableLiveData<File>()
    val groupImage: LiveData<File>
        get() = _groupImage

    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String>
        get() = _groupName

    private val _groupBio = MutableLiveData<String>()
    val groupBio: LiveData<String>
        get() = _groupBio

    private var isGroupNameVerified = false

    val isSearchEnabled = MutableLiveData<Boolean>()

    private val _isGroupEnabled = MutableLiveData(false)
    val isGroupEnabled: LiveData<Boolean>
        get() = _isGroupEnabled

    private val _isGoToSearchEnabled = MutableLiveData<Event<Unit>>()
    val isGoToSearchEnabled: LiveData<Event<Unit>>
        get() = _isGoToSearchEnabled

    // search
    private val _searchMemberName = MutableLiveData<String>()
    val searchMemberName: LiveData<String>
        get() = _searchMemberName

    private val _searchResultList = MutableLiveData<List<GroupCreateSearchResult>>()
    val searchResultList: LiveData<List<GroupCreateSearchResult>>
        get() = _searchResultList

    private val _inviteMemberList = MutableLiveData<List<GroupCreateSearchResult>>()
    val inviteMemberList: LiveData<List<GroupCreateSearchResult>>
        get() = _inviteMemberList

    fun setGroupImage(groupImage: File) {
        _groupImage.value = groupImage
    }

    fun setGroupName(name: String) {
        _groupName.value = name
    }

    fun setGroupBio(bio: String) {
        _groupBio.value = bio
    }

    fun checkGroupNameVerified() {
        val pattern = Pattern.compile("^[가-힣a-zA-Z0-9]{2,12}$")
        isGroupNameVerified = pattern.matcher(groupName.value ?: return).find() == true
    }

    fun checkGroupEnabled() {
        _isGroupEnabled.value = groupImage.value != null && groupName.value.isNullOrEmpty()
            .not() && groupBio.value.isNullOrEmpty().not() && isGroupNameVerified
    }

    fun requestGroupCreate() {
        if (isGroupEnabled.value != true) return

        val image = groupImage.value ?: return
        val name = groupName.value ?: return
        val bio = groupBio.value ?: return

        viewModelScope.launch {
            groupCreateUseCase.invoke(
                GroupCreateRequest(
                    image,
                    name,
                    bio,
                    isSearchEnabled.value ?: false
                )
            )
                .onSuccess { _isGoToSearchEnabled.value = Event(Unit) }
                .onFailure {
                    // TODO: 그룹 셍성 실패 토스트 띄우기
                }
        }
    }
}
